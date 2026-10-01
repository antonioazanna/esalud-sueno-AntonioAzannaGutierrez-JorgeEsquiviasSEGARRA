package es.uam.esalud.sleepapp

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import es.uam.esalud.sleepapp.audio.GrabadorAudio
import java.io.File

/**
 * Implementación REAL de la grabación, solo disponible en Android.
 *
 * En la sesión 3 se estudia por qué necesita un Context, qué permisos hay que
 * declarar en el AndroidManifest y qué le ocurre a la grabación cuando la
 * aplicación pasa a segundo plano.
 */
class GrabadorAndroid(private val context: Context) : GrabadorAudio {

    private var recorder: MediaRecorder? = null

    override fun iniciar(): String? {
        val fichero = File(context.filesDir, "sueno_${System.currentTimeMillis()}.3gp")

        val r = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            @Suppress("DEPRECATION")
            MediaRecorder()
        }

        return try {
            r.setAudioSource(MediaRecorder.AudioSource.MIC)
            r.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            r.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            r.setOutputFile(fichero.absolutePath)
            r.prepare()
            r.start()
            recorder = r
            fichero.absolutePath
        } catch (e: Exception) {
            r.release()
            null
        }
    }

    override fun detener() {
        recorder?.let { r ->
            try {
                r.stop()
            } catch (e: Exception) {
                // stop() lanza excepción si se llama sin haber grabado nada.
            } finally {
                r.release()
            }
        }
        recorder = null
    }
}
