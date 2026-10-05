package es.uam.esalud.sleepapp

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import es.uam.esalud.sleepapp.audio.GrabadorSimulado
import es.uam.esalud.sleepapp.datos.RepositorioJson
import kotlinx.browser.document

/**
 * Punto de entrada de la versión web.
 *
 * Es la versión que se despliega para que cualquiera pueda abrirla desde su
 * móvil, sea Android o iPhone. Usa el grabador simulado, porque en el
 * navegador no hay MediaRecorder de Android.
 *
 * El repositorio es el mismo `RepositorioJson` que en escritorio: la misma
 * clase, el mismo código. Lo único que cambia es dónde acaba el texto, que
 * aquí es el `localStorage` del navegador.
 */
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        App(
            grabador = GrabadorSimulado(),
            repositorio = RepositorioJson()
        )
    }
}
