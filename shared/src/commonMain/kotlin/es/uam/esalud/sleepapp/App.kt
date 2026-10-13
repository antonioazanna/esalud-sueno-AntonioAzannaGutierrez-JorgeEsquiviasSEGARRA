package es.uam.esalud.sleepapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import es.uam.esalud.sleepapp.audio.GrabadorAudio
import es.uam.esalud.sleepapp.datos.RepositorioSueno

/**
 * Punto de entrada común de la aplicación.
 *
 * Cada plataforma construye sus dependencias y las inyecta aquí. A partir de
 * este punto el código es idéntico en Android, escritorio y web.
 *
 * Cambio de la sesión 3: en vez de mostrar directamente MainScreen, se entra
 * por NavegacionApp, que decide qué pantalla toca.
 */
@Composable
fun App(
    grabador: GrabadorAudio,
    repositorio: RepositorioSueno
) {
    val viewModel = remember { SuenoViewModel(repositorio) }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            NavegacionApp(grabador = grabador, viewModel = viewModel)
        }
    }
}
