package es.uam.esalud.sleepapp

import es.uam.esalud.sleepapp.datos.RepositorioSueno
import es.uam.esalud.sleepapp.datos.SleepRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementación de RepositorioSueno que usa la base de datos.
 *
 * Fijaos en que no añade lógica: solo traduce entre los dos modelos y delega
 * en el DAO. Esa es toda su función, y es lo que permite que la pantalla no
 * sepa que Room existe.
 */
class RepositorioRoom(private val dao: SleepDao) : RepositorioSueno {

    // EJERCICIO 4
    // Los tres métodos son de una línea. Usad las funciones de traducción
    // aEntidad() y aModelo() que están en SleepDao.kt
    // Si os atascáis: pistas graduadas en el guion, apartado 4.4.

    override suspend fun guardar(registro: SleepRecord) {
        dao.insertar(registro.aEntidad())
    }

    override suspend fun borrar(id: Long) {
        dao.borrar(id)
    }

    override fun observarTodos(): Flow<List<SleepRecord>> {
        // Pista: dao.observarTodos() devuelve un Flow de entidades.
        // Hay que convertir cada lista de entidades en una lista de modelos:
        //     return dao.observarTodos().map { lista -> lista.map { ... } }
        //
        // Atención al `return`: este método devuelve un valor y su cuerpo va
        // entre llaves, así que Kotlin exige escribirlo. Los dos de arriba no
        // lo necesitan porque no devuelven nada.
        return dao.observarTodos().map { lista -> lista.map { it.aModelo() } }
    }
}
