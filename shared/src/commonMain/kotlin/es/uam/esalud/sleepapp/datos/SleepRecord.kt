package es.uam.esalud.sleepapp.datos

import es.uam.esalud.sleepapp.logica.Hora

/**
 * Un registro de sueño.
 *
 * En la sesión 2 esta clase se anotará como entidad de Room. De momento es
 * una data class normal, sin dependencias de ninguna base de datos.
 */
data class SleepRecord(
    val id: Long = 0,
    val inicio: Hora,
    val fin: Hora,
    val rutaAudio: String? = null,
    val calidadPercibida: Int? = null
)
