package es.uam.esalud.sleepapp.datos

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

/**
 * Repositorio que guarda los registros como texto JSON.
 *
 * Funciona exactamente igual que [RepositorioEnMemoria] —mantiene la lista en
 * un `MutableStateFlow` y la expone como `Flow`— con una diferencia: cada vez
 * que la lista cambia, la convierte a texto y se la entrega al almacén de la
 * plataforma, y al arrancar hace el camino inverso.
 *
 * Toda esta clase es código común. Se escribe una vez y vale para escritorio
 * y para web, porque lo único específico de cada plataforma está detrás de
 * [leerAlmacen] y [escribirAlmacen].
 *
 * JSON (JavaScript Object Notation) es un formato de texto para representar
 * datos estructurados. La lista de registros se convierte en algo así:
 *
 * ```json
 * [
 *   {
 *     "id": 1,
 *     "fechaMillis": 1759312800000,
 *     "inicio": { "horas": 23, "minutos": 30 },
 *     "fin": { "horas": 7, "minutos": 15 },
 *     "minutosDespierto": 20
 *   }
 * ]
 * ```
 *
 * La conversión no se escribe a mano: la genera el compilador a partir de la
 * anotación `@Serializable` sobre `SleepRecord` y `Hora`.
 */
class RepositorioJson : RepositorioSueno {

    /**
     * `prettyPrint` escribe el JSON con saltos de línea e indentación. No hace
     * falta para que funcione, pero permite abrir el fichero con un editor y
     * leerlo.
     *
     * `ignoreUnknownKeys` evita que la aplicación falle si encuentra datos
     * guardados por una versión anterior que tenía campos que ya no existen.
     */
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    /**
     * El objeto que sabe convertir una LISTA de registros, construido a partir
     * del `SleepRecord.serializer()` que ha generado el compilador.
     */
    private val serializador = ListSerializer(SleepRecord.serializer())

    private val registros = MutableStateFlow(cargar())

    /**
     * El siguiente identificador no puede empezar siempre en 1: si al arrancar
     * ya hay registros guardados, hay que continuar a partir del mayor que
     * haya, o se repetirían identificadores.
     */
    private var siguienteId = (registros.value.maxOfOrNull { it.id } ?: 0L) + 1

    private fun cargar(): List<SleepRecord> {
        val texto = leerAlmacen() ?: return emptyList()
        return try {
            json.decodeFromString(serializador, texto)
        } catch (e: Exception) {
            // El texto guardado no se puede interpretar: estaba corrupto, o lo
            // escribió una versión incompatible de la aplicación. Se empieza
            // de cero en lugar de impedir que la aplicación arranque.
            emptyList()
        }
    }

    private fun volcar() {
        escribirAlmacen(json.encodeToString(serializador, registros.value))
    }

    override suspend fun guardar(registro: SleepRecord) {
        registros.value = registros.value + registro.copy(id = siguienteId++)
        volcar()
    }

    override suspend fun borrar(id: Long) {
        registros.value = registros.value.filterNot { it.id == id }
        volcar()
    }

    override fun observarTodos(): Flow<List<SleepRecord>> = registros.asStateFlow()
}
