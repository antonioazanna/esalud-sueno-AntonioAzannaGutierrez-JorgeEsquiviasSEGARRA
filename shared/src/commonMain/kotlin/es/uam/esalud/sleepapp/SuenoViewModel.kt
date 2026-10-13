package es.uam.esalud.sleepapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.uam.esalud.sleepapp.datos.RepositorioSueno
import es.uam.esalud.sleepapp.datos.SleepRecord
import es.uam.esalud.sleepapp.logica.Hora
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SuenoViewModel(
    private val repositorio: RepositorioSueno
) : ViewModel() {

    val registros: StateFlow<List<SleepRecord>> =
        repositorio.observarTodos()
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    /**
     * Cambio de la sesión 3: además de las horas y la ruta del audio, recibe
     * la valoración que el paciente hace en el diálogo que aparece al detener
     * la grabación.
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
                    minutosDespierto = minutosDespierto
                )
            )
        }
    }

    fun borrar(id: Long) {
        viewModelScope.launch { repositorio.borrar(id) }
    }
}
