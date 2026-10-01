package es.uam.esalud.sleepapp.datos

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Guarda los registros en una lista, en memoria.
 *
 * Los datos se pierden al cerrar la app: es deliberado. Sirve para la sesión 1
 * (antes de ver persistencia) y es la única opción en la versión web, donde
 * Room no está disponible.
 */
class RepositorioEnMemoria : RepositorioSueno {

    private val registros = MutableStateFlow<List<SleepRecord>>(emptyList())
    private var siguienteId = 1L

    override suspend fun guardar(registro: SleepRecord) {
        registros.value = registros.value + registro.copy(id = siguienteId++)
    }

    override suspend fun borrar(id: Long) {
        registros.value = registros.value.filterNot { it.id == id }
    }

    override fun observarTodos(): Flow<List<SleepRecord>> = registros.asStateFlow()
}
