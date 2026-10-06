package es.uam.esalud.sleepapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.uam.esalud.sleepapp.datos.SleepRecord
import es.uam.esalud.sleepapp.logica.duracionEnMinutos

/**
 * Lista de registros de sueño.
 *
 * LazyColumn solo dibuja los elementos visibles en pantalla. Con diez registros
 * da igual; con dos mil es la diferencia entre una aplicación fluida y una que
 * no arranca.
 */
@Composable
fun ListaRegistros(
    registros: List<SleepRecord>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(registros) { registro ->
            Column(modifier = Modifier.padding(vertical = 8.dp)) {

                // EJERCICIO 6
                // Mostrad aquí, con elementos Text:
                //
                //   - el horario de la noche, con el formato "23:30 - 07:15".
                //     Pista: Hora ya sabe imprimirse sola dentro de un texto.
                //
                //   - la duración, usando DOS funciones que ya tenéis:
                //     duracionEnMinutos(), que escribisteis la semana pasada,
                //     y formatearMinutos(), que está en Formatos.kt
                //
                //   - un aviso "(grabación simulada)" solo si rutaAudio es null.
                //     Pista: un if normal sirve.
                //
                // Si os atascáis: pistas graduadas en el guion, parte 6.
                Text("${registro.inicio} - ${registro.fin}")
                Text("Duración: ${formatearMinutos(duracionEnMinutos(registro.inicio, registro.fin))}")
                if (registro.rutaAudio == null) {
                    Text("(grabación simulada)")
                }

            }
            HorizontalDivider()
        }
    }
}
