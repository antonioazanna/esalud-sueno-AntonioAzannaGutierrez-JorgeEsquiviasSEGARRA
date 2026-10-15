package es.uam.esalud.sleepapp.auth

/**
 * Convierte el código de error que devuelve Firebase en un mensaje que el
 * usuario pueda entender.
 *
 * `when` equivale a `match` en Python: compara el valor con cada caso, de
 * arriba abajo, y devuelve el texto del primero que coincide. Varios valores
 * separados por comas comparten el mismo resultado. `else` recoge todo lo que
 * no coincide con ningún caso.
 */
fun traducirError(codigo: String): String = when (codigo) {
    "INVALID_LOGIN_CREDENTIALS", "INVALID_PASSWORD", "EMAIL_NOT_FOUND" ->
        "Correo o contraseña incorrectos."
    "MISSING_PASSWORD" ->
        "Escribe una contraseña."
    "SIN_CONEXION" ->
        "No se ha podido conectar con el servidor. Comprueba la conexión a internet."

    // EJERCICIO 2
    // Si os atascáis: pistas graduadas en el guion, parte 3.
    //
    // Añadid aquí tres casos más, con el mismo formato que los de arriba:
    //   EMAIL_EXISTS   → ya existe una cuenta con ese correo
    //   WEAK_PASSWORD  → la contraseña debe tener al menos 6 caracteres
    //   INVALID_EMAIL  → el correo electrónico no es válido

    else ->
        "Error inesperado: $codigo"
}
