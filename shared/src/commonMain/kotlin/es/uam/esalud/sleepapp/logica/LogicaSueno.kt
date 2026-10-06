package es.uam.esalud.sleepapp.logica

/**
 * SESIÓN 1 - EJERCICIOS GUIADOS POR TESTS
 *
 * Implementad las dos funciones hasta que pasen todos los tests de
 * commonTest/.../LogicaSuenoTest.kt
 *
 * Para ejecutarlos: botón derecho sobre el fichero de tests > Run.
 * No hace falta emulador: son Kotlin puro.
 */

/**
 * Duración del sueño en minutos.
 *
 * Si [fin] es anterior o igual a [inicio] se entiende que se ha cruzado la
 * medianoche (por ejemplo: acostarse a las 23:30 y levantarse a las 07:15).
 */
fun duracionEnMinutos(inicio: Hora, fin: Hora): Int {
    val inicioMinutos = inicio.desdeMedianoche()
    val finMinutos = fin.desdeMedianoche()

    return if (finMinutos > inicioMinutos) {
        finMinutos - inicioMinutos
    } else {
        (24 * 60 - inicioMinutos) + finMinutos
    }
}

/**
 * Eficiencia del sueño: porcentaje del tiempo en cama que se ha pasado dormido.
 *
 * @throws IllegalArgumentException si [minutosEnCama] no es positivo.
 */
fun eficiencia(minutosDormido: Int, minutosEnCama: Int): Double {
    TODO("Ejercicio 2")
}
