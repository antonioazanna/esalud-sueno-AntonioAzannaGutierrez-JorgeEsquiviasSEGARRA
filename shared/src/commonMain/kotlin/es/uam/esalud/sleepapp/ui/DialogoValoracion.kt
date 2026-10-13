package es.uam.esalud.sleepapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.uam.esalud.sleepapp.logica.Hora
import es.uam.esalud.sleepapp.logica.duracionEnMinutos

/**
 * Diálogo que aparece al detener la grabación.
 *
 * Recoge lo que la grabación no puede medir y solo el paciente sabe: cuánto
 * tiempo estuvo despierto durante la noche y qué tal durmió. El registro no
 * se guarda hasta que se pulsa "Guardar"; "Cancelar" lo descarta.
 *
 * El diálogo no guarda nada por sí mismo: cuando el usuario pulsa "Guardar",
 * entrega los dos valores a quien lo muestra mediante `onGuardar`, y es
 * MainScreen quien decide qué hacer con ellos.
 */
@Composable
fun DialogoValoracion(
    inicio: Hora,
    fin: Hora,
    onGuardar: (calidad: Int, minutosDespierto: Int) -> Unit,
    onCancelar: () -> Unit
) {
    var calidad by remember { mutableStateOf(3) }
    var despierto by remember { mutableStateOf(0) }

    // No se puede estar despierto más tiempo del que se pasó en la cama:
    // el máximo del Slider es el tiempo en cama, con un tope de 3 horas.
    val tiempoEnCama = duracionEnMinutos(inicio, fin)
    val maximoDespierto = minOf(180, tiempoEnCama)

    AlertDialog(
        onDismissRequest = onCancelar,
        title = { Text("¿Qué tal has dormido?") },
        text = {
            Column {

                // --- Minutos despierto (DADO: usadlo de modelo para el ejercicio 4)

                Text("Tiempo despierto durante la noche: $despierto min")

                Slider(
                    value = despierto.toFloat(),
                    onValueChange = { nuevo -> despierto = nuevo.toInt() },
                    valueRange = 0f..maximoDespierto.toFloat(),
                    steps = (maximoDespierto / 5 - 1).coerceAtLeast(0)
                )

                Spacer(Modifier.height(16.dp))

                // EJERCICIO 4
                // Si os atascáis: pistas graduadas en el guion, parte 5.
                //
                // Añadid aquí un Text y un Slider para la calidad percibida del
                // sueño, con el mismo patrón que los de arriba.
                // El rango va de 1 a 5, así que valueRange = 1f..5f y steps = 3.
                // La variable de estado que hay que actualizar se llama `calidad`.
                Text("Calidad del sueño: $calidad / 5")

                Slider(
                    value = calidad.toFloat(),
                    onValueChange = { nuevo -> calidad = nuevo.toInt() },
                    valueRange = 1f..5f,
                    steps = 3
                )
            }
        },
        confirmButton = {
            Button(onClick = { onGuardar(calidad, despierto) }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onCancelar) {
                Text("Cancelar")
            }
        }
    )
}
