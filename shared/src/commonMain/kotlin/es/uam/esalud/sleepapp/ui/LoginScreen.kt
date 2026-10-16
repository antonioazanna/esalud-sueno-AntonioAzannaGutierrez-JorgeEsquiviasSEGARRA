
package es.uam.esalud.sleepapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import es.uam.esalud.sleepapp.auth.SesionViewModel

/**
 * Pantalla de inicio de sesión.
 *
 * Es la primera que se muestra. No navega por sí misma: cuando el inicio de
 * sesión tiene éxito, el SesionViewModel pasa a tener un usuario, y es
 * NavegacionApp quien lo detecta y lleva a la pantalla principal.
 */
@Composable
fun LoginScreen(sesion: SesionViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val mensajeError by sesion.error.collectAsState()
    val cargando by sesion.cargando.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Registro de sueño",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Inicia sesión para acceder a tus registros",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Modifier.height(24.dp))

        // --- Correo (DADO: usadlo de modelo para el ejercicio 3) -------------
        OutlinedTextField(
            value = email,
            onValueChange = { nuevo -> email = nuevo },
            label = { Text("Correo electrónico") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        // EJERCICIO 3
        // Si os atascáis: pistas graduadas en el guion, parte 3.
        //
        // Añadimos el campo de la contraseña siguiendo el mismo patrón
        // que el campo del correo electrónico.
        OutlinedTextField(
            value = password,
            onValueChange = { nuevo -> password = nuevo },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        mensajeError?.let { texto ->
            Text(
                text = texto,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(16.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    sesion.iniciarSesion(email.trim(), password)
                },
                enabled = !cargando
            ) {
                Text("Entrar")
            }

            OutlinedButton(
                onClick = {
                    sesion.crearCuenta(email.trim(), password)
                },
                enabled = !cargando
            ) {
                Text("Crear cuenta")
            }
        }

        if (cargando) {
            Spacer(Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
}

