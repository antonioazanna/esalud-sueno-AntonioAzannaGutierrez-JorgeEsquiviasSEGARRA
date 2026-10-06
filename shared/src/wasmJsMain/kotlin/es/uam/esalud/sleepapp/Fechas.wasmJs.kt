package es.uam.esalud.sleepapp

import es.uam.esalud.sleepapp.logica.Hora

// En Kotlin/Wasm no existe kotlin.js.Date, así que se llama a JavaScript
// directamente. Es la forma documentada de acceder al reloj del navegador.
private fun jsAhora(): Double = js("Date.now()")
private fun jsHoras(): Int = js("new Date().getHours()")
private fun jsMinutos(): Int = js("new Date().getMinutes()")

actual fun ahoraEnMillis(): Long = jsAhora().toLong()

actual fun horaActual(): Hora = Hora(jsHoras(), jsMinutos())
