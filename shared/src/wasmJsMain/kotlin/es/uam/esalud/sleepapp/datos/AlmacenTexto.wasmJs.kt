package es.uam.esalud.sleepapp.datos

import kotlinx.browser.window

/**
 * Idéntico al de `jsMain`. Hacen falta los dos ficheros porque `jsMain` y
 * `wasmJsMain` son dos destinos de compilación distintos y cada uno necesita
 * su propio `actual`.
 *
 * (A diferencia de `Fechas.wasmJs.kt`, aquí no hace falta bajar a JavaScript:
 * `kotlinx.browser` existe también para Wasm y la API es la misma.)
 */
private const val CLAVE = "sleepapp.registros"

actual fun leerAlmacen(): String? = window.localStorage.getItem(CLAVE)

actual fun escribirAlmacen(contenido: String) {
    window.localStorage.setItem(CLAVE, contenido)
}
