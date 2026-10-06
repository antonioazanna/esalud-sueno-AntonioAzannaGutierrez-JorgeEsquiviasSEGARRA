package es.uam.esalud.sleepapp.datos

/**
 * Almacén de texto de la plataforma.
 *
 * Es deliberadamente mínimo: solo sabe guardar y recuperar una cadena de
 * caracteres. No sabe nada de registros de sueño, ni de JSON, ni de listas.
 * Toda esa lógica vive en [RepositorioJson], en código común, y se escribe
 * una sola vez.
 *
 * Lo único que cambia de una plataforma a otra es DÓNDE se guarda ese texto:
 *
 *   - escritorio (jvmMain): un fichero en la carpeta del usuario
 *   - web (jsMain y wasmJsMain): el `localStorage` del navegador
 *   - Android (androidMain): no se usa, porque Android guarda con Room
 *
 * Es el mismo mecanismo `expect`/`actual` de la sesión 1: una declaración sin
 * cuerpo en código común, y una implementación por plataforma.
 */

/** Devuelve el texto guardado, o `null` si todavía no se ha guardado nada. */
expect fun leerAlmacen(): String?

/** Sobrescribe el texto guardado. */
expect fun escribirAlmacen(contenido: String)
