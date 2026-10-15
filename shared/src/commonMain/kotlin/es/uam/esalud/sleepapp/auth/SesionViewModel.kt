package es.uam.esalud.sleepapp.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Estado de la sesión: quién ha entrado, si hay una petición en curso y el
 * último mensaje de error.
 *
 * Es el mismo patrón que SuenoViewModel: la pantalla no habla con Firebase,
 * habla con el ViewModel, y observa sus StateFlow para redibujarse cuando
 * cambian.
 */
class SesionViewModel(
    private val servicio: ServicioAuth
) : ViewModel() {

    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario: StateFlow<Usuario?> = _usuario.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    fun iniciarSesion(email: String, password: String) =
        ejecutar { servicio.iniciarSesion(email, password) }

    fun crearCuenta(email: String, password: String) =
        ejecutar { servicio.crearCuenta(email, password) }

    fun cerrarSesion() {
        _usuario.value = null
        _error.value = null
    }

    /**
     * Lanza la petición sin bloquear la pantalla, igual que guardar() en
     * SuenoViewModel, y actualiza el estado con la respuesta.
     */
    private fun ejecutar(peticion: suspend () -> ResultadoAuth) {
        viewModelScope.launch {
            _cargando.value = true
            _error.value = null

            val resultado = peticion()

            _usuario.value = resultado.usuario
            _error.value = resultado.error?.let { codigo -> traducirError(codigo) }
            _cargando.value = false
        }
    }
}
