package es.uam.esalud.sleepapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.uam.esalud.sleepapp.datos.SleepRecord
import es.uam.esalud.sleepapp.logica.duracionEnMinutos
import es.uam.esalud.sleepapp.logica.eficiencia

@Composable
fun DetailScreen(
    registro: SleepRecord,
    onVolver: () -> Unit
) {
    val tiempoEnCama = duracionEnMinutos(
        registro.inicio,
        registro.fin
    )

    val tiempoDormido = (tiempoEnCama - registro.minutosDespierto)
        .coerceAtLeast(0)

    val eficienciaSueno = if (tiempoEnCama > 0) {
        eficiencia(tiempoDormido, tiempoEnCama)
    } else {
        0.0
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Detalle del registro",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = "${registro.inicio} - ${registro.fin}",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(12.dp))

        Text("Tiempo en cama: ${formatearMinutos(tiempoEnCama)}")
        Text("Tiempo despierto: ${formatearMinutos(registro.minutosDespierto)}")
        Text("Calidad percibida: ${registro.calidadPercibida} / 5")

        if (registro.rutaAudio == null) {
            Spacer(Modifier.height(8.dp))
            Text("Grabación simulada")
        }

        Spacer(Modifier.height(24.dp))
        HorizontalDivider()
        Spacer(Modifier.height(24.dp))

        Text(
            text = "Eficiencia del sueño",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = formatearPorcentaje(eficienciaSueno / 100.0),
            style = MaterialTheme.typography.headlineMedium
        )

        if (tiempoEnCama > 0 && eficienciaSueno < 85.0) {
            Text(
                text = "Eficiencia inferior al 85 %. " +
                        "Es un umbral orientativo, no un diagnóstico.",
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(Modifier.height(32.dp))

        OutlinedButton(onClick = onVolver) {
            Text("Volver")
        }
    }
}