package es.uam.esalud.sleepapp.audio

/**
 * Contrato de reproducción de audio.
 *
 * Es el mismo patrón que GrabadorAudio: la interfaz está en código común, y
 * cada plataforma aporta su implementación. En Android hay una real, con
 * MediaPlayer. En escritorio y web no hay grabaciones reales que reproducir,
 * así que se usa la simulada, que no hace nada.
 */
interface ReproductorAudio {

    /**
     * Reproduce el fichero de esa ruta. `alTerminar` se llama cuando la
     * reproducción acaba sola, al llegar al final, o si no se ha podido
     * reproducir; así la pantalla sabe que tiene que volver a su estado
     * inicial.
     */
    fun reproducir(ruta: String, alTerminar: () -> Unit)

    /** Detiene la reproducción y libera los recursos. */
    fun detener()
}

/** Implementación vacía, para las plataformas sin grabación real. */
class ReproductorSimulado : ReproductorAudio {

    override fun reproducir(ruta: String, alTerminar: () -> Unit) {
        alTerminar()
    }

    override fun detener() {
        // No hay nada que detener.
    }
}
