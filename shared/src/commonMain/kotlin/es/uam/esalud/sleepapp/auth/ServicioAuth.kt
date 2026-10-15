package es.uam.esalud.sleepapp.auth

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * El usuario que ha iniciado sesión.
 *
 * `uid` es el identificador que Firebase asigna a cada cuenta. No cambia
 * nunca, aunque el usuario cambie de correo, y es lo que se guarda en cada
 * registro para saber de quién es.
 */
data class Usuario(
    val uid: String,
    val email: String
)

/**
 * Resultado de una petición a Firebase: o bien un usuario, o bien un código
 * de error. Nunca las dos cosas a la vez.
 */
data class ResultadoAuth(
    val usuario: Usuario? = null,
    val error: String? = null
)

/**
 * Comunicación con Firebase Authentication.
 *
 * Firebase ofrece librerías propias para Android y para web, pero no una
 * común a las tres plataformas. Por eso aquí no se usan: se habla con
 * Firebase directamente por HTTP, mediante su API REST, igual que lo haría un
 * navegador. Así esta clase está en código común y funciona en Android, en
 * escritorio y en web sin cambios.
 *
 * Para las peticiones HTTP se usa Ktor, una librería de Kotlin
 * multiplataforma. Equivale a `requests` en Python:
 *
 *     requests.post(url, json={"email": ..., "password": ...})
 *
 * Cada petición envía un JSON con el correo y la contraseña, y Firebase
 * responde con otro JSON: los datos del usuario si todo ha ido bien, o un
 * código de error (por ejemplo, EMAIL_EXISTS) si no.
 */
class ServicioAuth(private val apiKey: String) {

    private val cliente = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    /** Crea una cuenta nueva con ese correo y esa contraseña. */
    suspend fun crearCuenta(email: String, password: String): ResultadoAuth =
        llamar("accounts:signUp", email, password)

    /** Comprueba el correo y la contraseña de una cuenta existente. */
    suspend fun iniciarSesion(email: String, password: String): ResultadoAuth =
        llamar("accounts:signInWithPassword", email, password)

    private suspend fun llamar(
        operacion: String,
        email: String,
        password: String
    ): ResultadoAuth {
        val url = "https://identitytoolkit.googleapis.com/v1/$operacion?key=$apiKey"

        return try {
            val respuesta = cliente.post(url) {
                contentType(ContentType.Application.Json)
                setBody(PeticionAuth(email, password, returnSecureToken = true))
            }

            if (respuesta.status.isSuccess()) {
                val datos: RespuestaCorrecta = respuesta.body()
                ResultadoAuth(usuario = Usuario(uid = datos.localId, email = datos.email))
            } else {
                // Firebase a veces añade una explicación tras el código:
                // "WEAK_PASSWORD : Password should be at least 6 characters".
                // Nos quedamos solo con el código.
                val datos: RespuestaError = respuesta.body()
                ResultadoAuth(error = datos.error.message.substringBefore(" "))
            }
        } catch (e: Exception) {
            // No ha habido respuesta: sin red, sin permiso de internet en
            // Android, o el servidor no es accesible.
            ResultadoAuth(error = "SIN_CONEXION")
        }
    }
}

// --- Formato de los mensajes JSON que se intercambian con Firebase ----------
// Los nombres de los campos son los que fija Firebase; no se pueden cambiar.

@Serializable
private data class PeticionAuth(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean
)

@Serializable
private data class RespuestaCorrecta(
    val localId: String,
    val email: String
)

@Serializable
private data class RespuestaError(val error: DetalleError)

@Serializable
private data class DetalleError(val message: String)
