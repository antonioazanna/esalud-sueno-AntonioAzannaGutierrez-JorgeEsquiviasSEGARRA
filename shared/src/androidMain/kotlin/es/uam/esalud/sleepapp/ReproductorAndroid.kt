package es.uam.esalud.sleepapp

import android.media.MediaPlayer
import es.uam.esalud.sleepapp.audio.ReproductorAudio

/**
 * Implementación REAL de la reproducción, solo disponible en Android.
 *
 * MediaPlayer es la pieza simétrica de MediaRecorder: uno escribe un fichero
 * de audio, el otro lo lee. Igual que MediaRecorder, ocupa recursos del
 * sistema mientras existe, así que hay que liberarlo con release() en cuanto
 * deja de hacer falta.
 */
class ReproductorAndroid : ReproductorAudio {

    private var player: MediaPlayer? = null

    override fun reproducir(ruta: String, alTerminar: () -> Unit) {
        detener()

        val p = MediaPlayer()
        try {
            p.setDataSource(ruta)
            p.prepare()
            p.setOnCompletionListener {
                detener()
                alTerminar()
            }
            p.start()
            player = p
        } catch (e: Exception) {
            // El fichero no existe o no se puede leer.
            p.release()
            alTerminar()
        }
    }

    override fun detener() {
        player?.let { p ->
            try {
                p.stop()
            } catch (e: Exception) {
                // stop() falla si el reproductor no llegó a arrancar.
            } finally {
                p.release()
            }
        }
        player = null
    }
}
