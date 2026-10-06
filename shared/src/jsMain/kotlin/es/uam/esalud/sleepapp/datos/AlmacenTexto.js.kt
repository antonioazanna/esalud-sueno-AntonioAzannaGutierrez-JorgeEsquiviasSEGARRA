package es.uam.esalud.sleepapp.datos

import kotlinx.browser.window

/**
 * En el navegador el almacén es `localStorage`: un diccionario de cadenas de
 * texto que el navegador conserva entre visitas, asociado al dominio de la
 * página. Sobrevive a cerrar la pestaña y a cerrar el navegador.
 *
 * Tiene un límite de unos 5 MB por dominio, de sobra para unos cuantos
 * registros de sueño. Si el usuario borra los datos de navegación, se pierde.
 */
private const val CLAVE = "sleepapp.registros"

actual fun leerAlmacen(): String? = window.localStorage.getItem(CLAVE)

actual fun escribirAlmacen(contenido: String) {
    window.localStorage.setItem(CLAVE, contenido)
}
