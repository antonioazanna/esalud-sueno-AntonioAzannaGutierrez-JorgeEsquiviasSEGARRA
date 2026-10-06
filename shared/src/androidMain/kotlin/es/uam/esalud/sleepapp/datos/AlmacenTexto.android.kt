package es.uam.esalud.sleepapp.datos

/**
 * Android no usa este almacén: su persistencia es Room, que es una base de
 * datos de verdad y permite consultas, no solo guardar y leer un bloque de
 * texto entero.
 *
 * Aun así hace falta escribir el `actual`: la regla de Kotlin Multiplatform es
 * que toda declaración `expect` tiene que tener una implementación en TODAS
 * las plataformas del proyecto, se use o no. Estas dos funciones nunca llegan
 * a ejecutarse, porque `MainActivity` construye `RepositorioRoom`.
 */
actual fun leerAlmacen(): String? = null

actual fun escribirAlmacen(contenido: String) {
    // Intencionadamente vacío. Ver el comentario de arriba.
}
