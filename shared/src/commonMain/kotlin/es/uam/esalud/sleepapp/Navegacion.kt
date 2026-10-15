
package es.uam.esalud.sleepapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import es.uam.esalud.sleepapp.audio.GrabadorAudio
import es.uam.esalud.sleepapp.audio.ReproductorAudio
import es.uam.esalud.sleepapp.auth.SesionViewModel
import es.uam.esalud.sleepapp.ui.DetailScreen
import es.uam.esalud.sleepapp.ui.LoginScreen
import es.uam.esalud.sleepapp.ui.MainScreen
import kotlinx.serialization.Serializable

/**
 * Las rutas de la aplicación.
 *
 * Cada pantalla es una clase. La de detalle lleva dentro el dato que necesita
 * recibir, así que el compilador comprueba que se pasa siempre y del tipo
 * correcto. Esto se llama navegación con rutas de tipo seguro.
 *
 * `object` en lugar de `data class` cuando la pantalla no recibe nada.
 *
 * Ruta nueva en la sesión 4: RutaLogin, que pasa a ser la pantalla inicial.
 */
@Serializable
object RutaLogin

@Serializable
object RutaPrincipal

@Serializable
data class RutaDetalle(val registroId: Long)

/**
 * Mapa de pantallas de la aplicación.
 *
 * Tres piezas:
 *
 *  - rememberNavController() crea el controlador que recuerda en qué pantalla
 *    estamos y cómo hemos llegado hasta aquí.
 *  - NavHost es el contenedor: declara qué pantalla se muestra al arrancar
 *    (startDestination) y qué rutas existen.
 *  - composable<Ruta> { } define cada pantalla.
 */
@Composable
fun NavegacionApp(
    grabador: GrabadorAudio,
    reproductor: ReproductorAudio,
    viewModel: SuenoViewModel,
    sesion: SesionViewModel
) {
    val navController = rememberNavController()
    val registros by viewModel.registros.collectAsState()
    val usuario by sesion.usuario.collectAsState()

    NavHost(
        navController = navController,
        startDestination = RutaLogin
    ) {
        composable<RutaLogin> {
            // LaunchedEffect ejecuta el bloque cada vez que cambia el valor
            // entre paréntesis. Cuando el inicio de sesión tiene éxito,
            // `usuario` deja de ser null y hay que ir a la pantalla principal.
            LaunchedEffect(usuario) {
                val actual = usuario
                if (actual != null) {
                    viewModel.cambiarUsuario(actual.uid)

                    // EJERCICIO 4
                    // Si os atascáis: pistas graduadas en el guion, parte 3.
                    //
                    // Navegad a RutaPrincipal eliminando RutaLogin del
                    // historial. El cierre de sesión, más abajo, hace lo mismo
                    // en sentido contrario: usadlo de modelo.
                    navController.navigate(RutaPrincipal) {
                        popUpTo(RutaLogin) {
                            inclusive = true
                        }
                    }
                }
            }
            LoginScreen(sesion = sesion)
        }

        composable<RutaPrincipal> {
            MainScreen(
                grabador = grabador,
                viewModel = viewModel,
                correoUsuario = usuario?.email ?: "",
                onCerrarSesion = {
                    sesion.cerrarSesion()
                    viewModel.cambiarUsuario(null)

                    // Al cerrar sesión se vuelve al inicio de sesión y se
                    // elimina la pantalla principal del historial: el botón
                    // de volver no debe devolver a los datos del usuario
                    // que acaba de salir.
                    navController.navigate(RutaLogin) {
                        popUpTo(RutaPrincipal) { inclusive = true }
                    }
                },
                onRegistroClick = { id ->
                    navController.navigate(RutaDetalle(id))
                }
            )
        }

        composable<RutaDetalle> { entrada ->
            val ruta: RutaDetalle = entrada.toRoute()
            val registro = registros.find { it.id == ruta.registroId }

            if (registro != null) {
                DetailScreen(
                    registro = registro,
                    reproductor = reproductor,
                    onVolver = { navController.popBackStack() }
                )
            }
        }
    }
}

