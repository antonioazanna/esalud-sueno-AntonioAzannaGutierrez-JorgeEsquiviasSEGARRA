package es.uam.esalud.sleepapp

import es.uam.esalud.sleepapp.logica.Hora
import kotlin.js.Date

actual fun ahoraEnMillis(): Long = Date.now().toLong()

actual fun horaActual(): Hora {
    val d = Date()
    return Hora(d.getHours(), d.getMinutes())
}
