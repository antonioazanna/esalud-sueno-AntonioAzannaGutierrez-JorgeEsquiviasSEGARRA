package es.uam.esalud.sleepapp.datos

import es.uam.esalud.sleepapp.logica.Hora
import kotlinx.serialization.Serializable

/**
 * Un registro de sueño: una noche.
 *
 * Campo nuevo en la sesión 3:
 *   - minutosDespierto: cuánto tiempo estuvo el paciente despierto durante la
 *     noche. Es lo que permite calcular la eficiencia del sueño, porque el
 *     tiempo en cama y el tiempo dormido no son lo mismo.
 *
 * La anotación `@Serializable` le pide al compilador que genere el código que
 * convierte esta clase a texto JSON y vuelta. Es lo que permite guardar los
 * registros en escritorio y en web (ver `RepositorioJson`). El tipo `Hora`
 * también tiene que estar anotado, porque es un campo de esta clase.
 */
@Serializable
data class SleepRecord(
    val id: Long = 0,
    val fechaMillis: Long,
    val inicio: Hora,
    val fin: Hora,
    val rutaAudio: String? = null,
    val calidadPercibida: Int? = null,
    val minutosDespierto: Int = 0
)
