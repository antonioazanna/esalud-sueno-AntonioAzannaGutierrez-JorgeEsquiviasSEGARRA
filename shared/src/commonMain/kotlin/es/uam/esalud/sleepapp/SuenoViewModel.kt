package es.uam.esalud.sleepapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.uam.esalud.sleepapp.datos.RepositorioSueno
import es.uam.esalud.sleepapp.datos.SleepRecord
import es.uam.esalud.sleepapp.logica.Hora
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SuenoViewModel(
    private val repositorio: RepositorioSueno
) : ViewModel() {

    /**
     * Cambio de la sesión 4: el uid del usuario que ha iniciado sesión, o
     * null si no hay nadie.
     */
    private val usuarioActual = MutableStateFlow<String?>(null)

    /**
     * Cambio de la sesión 4: ya no son todos los registros, sino solo los del
     * usuario actual.
     *
     * `combine` junta dos flujos: la lista completa que da el repositorio y el
     * usuario actual. Cada vez que cambia cualquiera de los dos —se guarda un
     * registro, o entra otro usuario— se vuelve a ejecutar el bloque, y la
     * pantalla recibe la lista nueva.
     */
    val registros: StateFlow<List<SleepRecord>> =
        combine(repositorio.observarTodos(), usuarioActual) { lista, uid ->
            // EJERCICIO 5
            // Si os atascáis: pistas graduadas en el guion, parte 4.
            //
            // Ahora se devuelve la lista entera. Sustituid la línea siguiente
            // por una que devuelva solo los registros cuyo usuarioId sea uid.
            lista
        }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    /** Se llama al iniciar sesión (con el uid) y al cerrarla (con null). */
    fun cambiarUsuario(uid: String?) {
        usuarioActual.value = uid
    }

    /**
     * Cambio de la sesión 3: además de las horas y la ruta del audio, recibe
     * la valoración que el paciente hace en el diálogo que aparece al detener
     * la grabación.
     *
     * Cambio de la sesión 4: cada registro se guarda con el uid de quien lo
     * crea.
     */
    fun guardar(
        inicio: Hora,
        fin: Hora,
        rutaAudio: String?,
        calidadPercibida: Int,
        minutosDespierto: Int
    ) {
        viewModelScope.launch {
            repositorio.guardar(
                SleepRecord(
                    fechaMillis = ahoraEnMillis(),
                    inicio = inicio,
                    fin = fin,
                    rutaAudio = rutaAudio,
                    calidadPercibida = calidadPercibida,
                    minutosDespierto = minutosDespierto,
                    usuarioId = usuarioActual.value ?: ""
                )
            )
        }
    }

    fun borrar(id: Long) {
        viewModelScope.launch { repositorio.borrar(id) }
    }
}
