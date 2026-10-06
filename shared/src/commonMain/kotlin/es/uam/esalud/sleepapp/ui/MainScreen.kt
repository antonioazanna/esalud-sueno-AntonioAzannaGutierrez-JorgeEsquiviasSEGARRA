package es.uam.esalud.sleepapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.uam.esalud.sleepapp.SuenoViewModel
import es.uam.esalud.sleepapp.audio.GrabadorAudio
import es.uam.esalud.sleepapp.horaActual
import es.uam.esalud.sleepapp.logica.Hora
import es.uam.esalud.sleepapp.nombrePlataforma

/**
 * SESIÓN 1 - PANTALLA PRINCIPAL
 *
 * Los TODO numerados son vuestros. El resto está resuelto.
 */
@Composable
fun MainScreen(
    grabador: GrabadorAudio,
    viewModel: SuenoViewModel
) {

    // `remember` + `mutableStateOf` = una variable que, al cambiar, hace que
    // Compose vuelva a dibujar lo que dependa de ella.
    var grabando by remember { mutableStateOf(false) }
    var ultimaRuta by remember { mutableStateOf<String?>(null) }
    var horaInicio by remember { mutableStateOf(Hora(0, 0)) }
    val registros by viewModel.registros.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Registro de sueño",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Ejecutando en: " + nombrePlataforma(),
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = {
                    ultimaRuta=grabador.iniciar()
                    horaInicio = horaActual()
                    grabando=true
                },
                enabled =! grabando
            ) {
                Text("Iniciar grabación")
            }
            Button(
                onClick = {
                    grabador.detener()
                    grabando=false
                    viewModel.guardar(
                        inicio = horaInicio,
                        fin = horaActual(),
                        rutaAudio = ultimaRuta
                    )
                },
                enabled = grabando
            ){
                Text("Detener grabación")
            }
        }

        Spacer(Modifier.height(32.dp))

        val mensaje = if (grabando) "Grabando..." else "En reposo"
        Text(text = mensaje)

        ListaRegistros(registros)
    }
}
