
package es.uam.esalud.sleepapp

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import es.uam.esalud.sleepapp.audio.ReproductorAudio
import java.io.File

/**
 * Implementación REAL de la reproducción, solo disponible en Android.
 *
 * MediaPlayer lee un fichero de audio. Igual que MediaRecorder,
 * ocupa recursos del sistema y hay que liberarlo con release().
 */
class ReproductorAndroid : ReproductorAudio {

    private var player: MediaPlayer? = null

    override fun reproducir(ruta: String, alTerminar: () -> Unit) {
        detener()

        val fichero = File(ruta)

        Log.d(
            "ReproductorAndroid",
            "Ruta: $ruta | Existe: ${fichero.exists()} | " +
                    "Tamaño: ${if (fichero.exists()) fichero.length() else -1} bytes"
        )

        if (!fichero.exists() || !fichero.isFile || fichero.length() == 0L) {
            Log.e("ReproductorAndroid", "El archivo no existe, no es válido o está vacío")
            alTerminar()
            return
        }

        val p = MediaPlayer()

        try {
            p.setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )

            p.setOnCompletionListener {
                Log.d("ReproductorAndroid", "Reproducción finalizada")
                detener()
                alTerminar()
            }

            p.setOnErrorListener { _, what, extra ->
                Log.e(
                    "ReproductorAndroid",
                    "Error de MediaPlayer: what=$what, extra=$extra"
                )
                detener()
                alTerminar()
                true
            }

            p.setDataSource(fichero.absolutePath)
            p.prepare()
            p.start()

            player = p

            Log.d("ReproductorAndroid", "Reproducción iniciada correctamente")
        } catch (e: Exception) {
            Log.e("ReproductorAndroid", "No se pudo reproducir el archivo", e)

            try {
                p.release()
            } catch (_: Exception) {
                // El reproductor ya podría estar liberado.
            }

            alTerminar()
        }
    }

    override fun detener() {
        player?.let { p ->
            try {
                if (p.isPlaying) {
                    p.stop()
                }
            } catch (e: Exception) {
                Log.w("ReproductorAndroid", "No se pudo detener normalmente", e)
            } finally {
                p.release()
            }
        }

        player = null
    }
}

