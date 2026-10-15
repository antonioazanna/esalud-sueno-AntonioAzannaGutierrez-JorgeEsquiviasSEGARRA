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

/**
 * Implementación de mentira, para desarrollar sin micrófono ni permisos.
 *
 * Cambio de la sesión 4: devuelve null en lugar de un nombre de fichero
 * inventado. No graba nada, así que no hay ningún fichero; con null, el
 * registro queda marcado como grabación simulada y la pantalla de detalle no
 * ofrece reproducirlo.
 */
class GrabadorSimulado : GrabadorAudio {

    override fun iniciar(): String? {
        return null
    }

    override fun detener() {
        // No hay nada que liberar.
    }
}
