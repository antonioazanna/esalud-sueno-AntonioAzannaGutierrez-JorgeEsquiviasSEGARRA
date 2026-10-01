package es.uam.esalud.sleepapp.datos

import kotlinx.coroutines.flow.Flow

/**
 * Contrato de persistencia.
 *
 * La pantalla y el ViewModel dependen SOLO de esta interfaz, nunca de Room ni
 * de Firestore. Gracias a eso, en la sesión 4 se puede añadir Firebase
 * escribiendo una clase nueva sin tocar ni una línea de la interfaz de usuario.
 *
 * Implementaciones previstas:
 *   - RepositorioEnMemoria  (ya incluida; suficiente para la sesión 1 y para web)
 *   - RepositorioRoom       (sesión 2, Android y escritorio)
 *   - RepositorioFirestore  (sesión 4, solo Android)
 */
interface RepositorioSueno {

    suspend fun guardar(registro: SleepRecord)

    suspend fun borrar(id: Long)

    fun observarTodos(): Flow<List<SleepRecord>>
}
