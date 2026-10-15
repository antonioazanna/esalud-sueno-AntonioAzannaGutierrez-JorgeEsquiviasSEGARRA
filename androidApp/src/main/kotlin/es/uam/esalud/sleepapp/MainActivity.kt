package es.uam.esalud.sleepapp

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // --- Permiso de grabación -------------------------------------------
        // registerForActivityResult prepara una petición de permiso y define
        // qué hacer con la respuesta del usuario. Solo la prepara: no la lanza.
        val peticionPermiso = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { concedido ->
            if (!concedido) {
                Toast.makeText(
                    this,
                    "Sin permiso de micrófono no se puede grabar",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        // Sesión 3, ejercicio 2
        peticionPermiso.launch(Manifest.permission.RECORD_AUDIO)

        // --- Dependencias de la aplicación ----------------------------------
        val repositorio = crearRepositorioRoom(applicationContext)
        val grabador = GrabadorAndroid(applicationContext)
        val reproductor = ReproductorAndroid()     // sesión 4

        setContent {
            App(
                grabador = grabador,
                repositorio = repositorio,
                reproductor = reproductor
            )
        }
    }
}
