package es.uam.esalud.sleepapp.datos

import java.io.File

/**
 * En escritorio el almacén es un fichero de texto dentro de la carpeta
 * personal del usuario:
 *
 *   Linux y macOS:  ~/.sleepapp/registros.json
 *   Windows:        C:\Users\NOMBRE\.sleepapp\registros.json
 *
 * Se puede abrir con cualquier editor de texto. Es la forma más rápida de
 * comprobar que la aplicación está guardando de verdad.
 */
private val fichero: File by lazy {
    val carpeta = File(System.getProperty("user.home"), ".sleepapp")
    carpeta.mkdirs()
    File(carpeta, "registros.json")
}

actual fun leerAlmacen(): String? =
    if (fichero.exists()) fichero.readText() else null

actual fun escribirAlmacen(contenido: String) {
    fichero.writeText(contenido)
}
