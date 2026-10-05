package es.uam.esalud.sleepapp.logica

import kotlinx.serialization.Serializable

/**
 * Una hora del día, sin fecha.
 *
 * `data class` es la forma que tiene Kotlin de declarar un tipo que solo
 * agrupa datos. Equivale, más o menos, a un `@dataclass` de Python.
 *
 * El bloque `init` se ejecuta al construir el objeto: `require` lanza una
 * excepción si la condición no se cumple. Así es imposible que exista un
 * objeto Hora inválido.
 *
 * `@Serializable` por la misma razón que en `SleepRecord`: es un campo suyo y
 * hay que saber convertirlo a JSON. Se guarda como `{ "horas": 23, "minutos": 30 }`.
 */
@Serializable
data class Hora(val horas: Int, val minutos: Int) {

    init {
        require(horas in 0..23) { "Horas fuera de rango: $horas" }
        require(minutos in 0..59) { "Minutos fuera de rango: $minutos" }
    }

    /** Minutos transcurridos desde la medianoche. */
    fun desdeMedianoche(): Int = horas * 60 + minutos

    override fun toString(): String =
        horas.toString().padStart(2, '0') + ":" + minutos.toString().padStart(2, '0')

    companion object {
        /**
         * Camino inverso a [desdeMedianoche]: reconstruye la hora a partir de
         * los minutos transcurridos desde medianoche.
         *
         * Hace falta porque la base de datos no puede guardar un objeto Hora:
         * guarda un número, y al leerlo hay que volver a construir el objeto.
         */
        fun desdeMinutos(minutosTotales: Int): Hora =
            Hora(minutosTotales / 60, minutosTotales % 60)
    }
}
