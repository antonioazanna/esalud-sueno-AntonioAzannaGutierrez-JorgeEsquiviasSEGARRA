package es.uam.esalud.sleepapp.datos

import es.uam.esalud.sleepapp.logica.Hora
import kotlinx.serialization.Serializable

/**
 * Un registro de sueño: una noche.
 *
 * Es la misma clase de la sesión 1, con dos campos nuevos:
 *   - fechaMillis: cuándo se hizo el registro, para poder ordenarlos.
 *   - calidadPercibida ya estaba, y se rellenará en la sesión 3.
 *
 * Fijaos en que la fecha NO se guarda escrita ("03/04/2026"), sino como el
 * número de milisegundos transcurridos desde el 1 de enero de 1970, que es la
 * convención universal para representar un instante. Guardarla escrita
 * ("03/04/2026") impediría ordenarla y sería ambigua entre países.
 *
 * Las horas, en cambio, sí son objetos Hora: es como se razona sobre el sueño.
 * La traducción a números para la base de datos ocurre en otro sitio.
 *
 * La anotación `@Serializable` le pide al compilador que genere el código que
 * convierte esta clase a texto JSON y vuelta. Es lo que permite guardar los
 * registros en escritorio y en web (ver `RepositorioJson`).
 */
@Serializable
data class SleepRecord(
    val id: Long = 0,
    val fechaMillis: Long,
    val inicio: Hora,
    val fin: Hora,
    val rutaAudio: String? = null,
    val calidadPercibida: Int? = null
)
