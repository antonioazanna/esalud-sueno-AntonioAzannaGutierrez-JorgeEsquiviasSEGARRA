package es.uam.esalud.sleepapp.logica

/**
 * Una hora del día, sin fecha.
 *
 * `data class` es la forma que tiene Kotlin de declarar un tipo que solo
 * agrupa datos. Equivale, más o menos, a un `@dataclass` de Python.
 *
 * El bloque `init` se ejecuta al construir el objeto: `require` lanza una
 * excepción si la condición no se cumple. Así es imposible que exista un
 * objeto Hora inválido.
 */
data class Hora(val horas: Int, val minutos: Int) {

    init {
        require(horas in 0..23) { "Horas fuera de rango: $horas" }
        require(minutos in 0..59) { "Minutos fuera de rango: $minutos" }
    }

    /** Minutos transcurridos desde la medianoche. */
    fun desdeMedianoche(): Int = horas * 60 + minutos

    override fun toString(): String =
        horas.toString().padStart(2, '0') + ":" + minutos.toString().padStart(2, '0')
}
