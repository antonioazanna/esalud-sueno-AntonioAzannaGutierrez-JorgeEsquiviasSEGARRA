package es.uam.esalud.sleepapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import es.uam.esalud.sleepapp.audio.GrabadorAudio
import es.uam.esalud.sleepapp.ui.DetailScreen
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
 */
@Serializable
object RutaPrincipal

@Serializable
data class RutaDetalle(val registroId: Long)

/**
 * Mapa de pantallas de la aplicación. Viene dado.
 *
 * Tres piezas:
 *
 *  - rememberNavController() crea el controlador que recuerda en qué pantalla
 *    estamos y cómo hemos llegado hasta aquí.
 *  - NavHost es el contenedor: declara qué pantalla se muestra al arrancar
 *    (startDestination) y qué rutas existen.
 *  - composable<Ruta> { } define cada pantalla.
 *
 * Para ir al detalle se construye el objeto con el dato dentro,
 * `navigate(RutaDetalle(7))`, y la pantalla de destino lo recupera con
 * `toRoute()`. Así se pasa información de una pantalla a otra.
 */
@Composable
fun NavegacionApp(
    grabador: GrabadorAudio,
    viewModel: SuenoViewModel
) {
    val navController = rememberNavController()
    val registros by viewModel.registros.collectAsState()

    NavHost(
        navController = navController,
        startDestination = RutaPrincipal
    ) {

        composable<RutaPrincipal> {
            MainScreen(
                grabador = grabador,
                viewModel = viewModel,
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
                    onVolver = { navController.popBackStack() }
                )
            }
        }
    }
}
