
package es.uam.esalud.sleepapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import es.uam.esalud.sleepapp.audio.GrabadorAudio
import es.uam.esalud.sleepapp.audio.ReproductorAudio
import es.uam.esalud.sleepapp.audio.ReproductorSimulado
import es.uam.esalud.sleepapp.auth.ServicioAuth
import es.uam.esalud.sleepapp.auth.SesionViewModel
import es.uam.esalud.sleepapp.datos.RepositorioSueno
import org.jetbrains.compose.resources.painterResource
import sleepapp.shared.generated.resources.Res
import sleepapp.shared.generated.resources.fondo


/**
 * Punto de entrada común de la aplicación.
 *
 * Cada plataforma construye sus dependencias y las inyecta aquí. A partir de
 * este punto el código es idéntico en Android, escritorio y web.
 *
 * Cambios de la sesión 4:
 *   - SesionViewModel, con el servicio de autenticación.
 *   - El reproductor de audio. Tiene un valor por defecto, el simulado, así
 *     que escritorio y web no necesitan pasarlo; solo Android aporta el real.
 *   - La imagen de fondo (ejercicio 7).
 */
@Composable
fun App(
    grabador: GrabadorAudio,
    repositorio: RepositorioSueno,
    reproductor: ReproductorAudio = ReproductorSimulado()
) {
    val viewModel = remember { SuenoViewModel(repositorio) }
    val sesion = remember { SesionViewModel(ServicioAuth(FIREBASE_API_KEY)) }

    MaterialTheme {
        // Box apila sus elementos uno encima de otro: primero el color de
        // fondo, encima la imagen y encima de todo, la aplicación.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // EJERCICIO 7
            // Si os atascáis: pistas graduadas en el guion, parte 6.
            //
            // Añadid aquí un Image que muestre Res.drawable.fondo ocupando
            // toda la pantalla, recortado para llenarla (ContentScale.Crop)
            // y semitransparente (alpha = 0.25f) para que el texto se lea.
            Image(
                painter = painterResource(Res.drawable.fondo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alpha = 0.25f,
                modifier = Modifier.fillMaxSize()
            )

            // Transparente para que se vea la imagen que hay debajo.
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground
            ) {
                NavegacionApp(
                    grabador = grabador,
                    reproductor = reproductor,
                    viewModel = viewModel,
                    sesion = sesion
                )
            }
        }
    }
}


