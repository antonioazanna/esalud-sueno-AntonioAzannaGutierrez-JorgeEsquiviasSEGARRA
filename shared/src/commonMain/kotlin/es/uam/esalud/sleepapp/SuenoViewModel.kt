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

/**
 * El ViewModel: el intermediario entre los datos y la pantalla.
 *
 * Recibe un RepositorioSueno, no una base de datos concreta. Por eso el mismo
 * ViewModel vale en Android (con Room), en escritorio y en web (en memoria).
 */
class SuenoViewModel(
    private val repositorio: RepositorioSueno
) : ViewModel() {

    /**
     * DADO. La lista de registros, lista para que la pantalla la dibuje.
     *
     * observarTodos() devuelve un flujo que emite una lista nueva cada vez que
     * cambia la base de datos. stateIn lo convierte en un flujo que además
     * RECUERDA el último valor, que es lo que necesita la pantalla: cuando se
     * dibuja, tiene que haber algo que mostrar aunque en ese instante no esté
     * llegando nada nuevo.
     */
    val registros: StateFlow<List<SleepRecord>> =
        repositorio.observarTodos()
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    /**
     * EJERCICIO 5
     *
     * Construid un SleepRecord con los datos que recibe la función y guardadlo
     * con el repositorio. Es una sola instrucción.
     *
     * Para la fecha usad ahoraEnMillis().
     * El id no se pasa: lo genera la base de datos.
     * calidadPercibida tampoco: se rellenará en la sesión 3.
     */
    fun guardar(inicio: Hora, fin: Hora, rutaAudio: String?) {
        viewModelScope.launch {
            // TODO (ejercicio 5)
            // Construid un SleepRecord con los datos recibidos y pasádselo a
            // repositorio.guardar(...). La fecha es ahoraEnMillis().
            // Si os atascáis: pistas graduadas en el guion, apartado 5.4.
        }
    }

    fun borrar(id: Long) {
        viewModelScope.launch { repositorio.borrar(id) }
    }
}
