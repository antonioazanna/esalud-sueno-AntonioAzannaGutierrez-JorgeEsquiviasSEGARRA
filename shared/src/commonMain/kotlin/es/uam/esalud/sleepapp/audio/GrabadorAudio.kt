package es.uam.esalud.sleepapp.audio

/**
 * Contrato de grabación de audio.
 *
 * La interfaz vive en código común: la pantalla no sabe (ni debe saber) si por
 * debajo hay un MediaRecorder de Android o una simulación de escritorio.
 *
 * Este es el patrón central de la asignatura: la lógica clínica es universal,
 * el acceso al sensor no lo es.
 */
interface GrabadorAudio {

    /** Comienza a grabar. Devuelve la ruta del fichero, o null si falla. */
    fun iniciar(): String?

    /** Detiene la grabación y libera los recursos. */
    fun detener()
}

/** Implementación de mentira, para desarrollar sin micrófono ni permisos. */
class GrabadorSimulado : GrabadorAudio {

    private var contador = 0

    override fun iniciar(): String? {
        contador++
        return "simulado_$contador.3gp"
    }

    override fun detener() {
        // No hay nada que liberar.
    }
}
