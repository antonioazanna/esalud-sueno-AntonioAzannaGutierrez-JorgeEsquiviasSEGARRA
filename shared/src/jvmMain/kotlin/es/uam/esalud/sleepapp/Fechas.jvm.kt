package es.uam.esalud.sleepapp

import es.uam.esalud.sleepapp.logica.Hora
import java.util.Calendar

actual fun ahoraEnMillis(): Long = System.currentTimeMillis()

actual fun horaActual(): Hora {
    val c = Calendar.getInstance()
    return Hora(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE))
}
