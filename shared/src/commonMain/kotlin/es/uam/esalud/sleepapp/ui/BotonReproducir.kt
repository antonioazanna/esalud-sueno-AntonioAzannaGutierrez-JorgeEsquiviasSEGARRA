package es.uam.esalud.sleepapp.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import es.uam.esalud.sleepapp.audio.ReproductorAudio

/**
 * Botón para escuchar la grabación de una noche.
 *
 * Solo aparece si el registro tiene audio, es decir, si se grabó de verdad.
 * En escritorio y web el grabador es simulado y la ruta es null, así que el
 * botón no se muestra.
 */
@Composable
fun BotonReproducir(
    ruta: String?,
    reproductor: ReproductorAudio
) {
    var reproduciendo by remember { mutableStateOf(false) }

    // Si el usuario sale de la pantalla mientras suena, se detiene. Es el
    // ciclo de vida de la sesión 3 aplicado a un elemento de la pantalla.
    DisposableEffect(Unit) {
        onDispose { reproductor.detener() }
    }

    if (ruta != null) {
        // EJERCICIO 6
        // Si os atascáis: pistas graduadas en el guion, parte 5.
        //
        // Un Button que, al pulsarlo:
        //   - si está reproduciendo: detiene el reproductor y pone
        //     reproduciendo a false
        //   - si no: pone reproduciendo a true y llama a
        //     reproductor.reproducir(ruta, alTerminar = { reproduciendo = false })
        // Su texto es "Detener reproducción" o "Reproducir grabación" según
        // el valor de reproduciendo.
    }
}
