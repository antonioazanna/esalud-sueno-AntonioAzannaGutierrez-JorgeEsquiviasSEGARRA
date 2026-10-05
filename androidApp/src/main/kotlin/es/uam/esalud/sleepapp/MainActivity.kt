package es.uam.esalud.sleepapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

/**
 * Punto de entrada de la versión Android.
 *
 * Cambio de la sesión 2: el repositorio ya no es el de memoria, sino el de
 * Room. Se obtiene con crearRepositorioRoom(), que está en AppDatabase.kt, en
 * el módulo shared.
 *
 * El resto de la aplicación no se entera del cambio: App() sigue recibiendo
 * un RepositorioSueno, como antes.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repositorio = crearRepositorioRoom(applicationContext)
        val grabador = GrabadorAndroid(applicationContext)

        setContent {
            App(grabador = grabador, repositorio = repositorio)
        }
    }
}
