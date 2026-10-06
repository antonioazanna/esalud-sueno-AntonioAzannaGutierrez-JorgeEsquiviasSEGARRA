package es.uam.esalud.sleepapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import es.uam.esalud.sleepapp.audio.GrabadorAudio
import es.uam.esalud.sleepapp.datos.RepositorioSueno
import es.uam.esalud.sleepapp.ui.MainScreen
import androidx.compose.runtime.remember

/**
 * Punto de entrada COMÚN de la aplicación.
 *
 * Cada plataforma construye sus propias dependencias (el grabador real o el
 * simulado, el repositorio de Room o el de memoria) y las inyecta aquí.
 * A partir de este punto, el código es idéntico en Android, escritorio y web.
 */
@Composable
fun App(
    grabador: GrabadorAudio,
    repositorio: RepositorioSueno
) {
    val viewModel = remember { SuenoViewModel(repositorio) }
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MainScreen(grabador = grabador, viewModel = viewModel)
        }
    }
}
