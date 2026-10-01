package es.uam.esalud.sleepapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import es.uam.esalud.sleepapp.datos.RepositorioEnMemoria

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Aquí se construyen las dependencias propias de Android y se inyectan
        // en el App() común. En la sesión 2 el repositorio en memoria se
        // sustituye por el de Room.
        val grabador = GrabadorAndroid(applicationContext)
        val repositorio = RepositorioEnMemoria()

        setContent {
            App(grabador = grabador, repositorio = repositorio)
        }
    }
}
