package es.uam.esalud.sleepapp.ui

/** Convierte una cantidad de minutos en un texto del tipo "7h 45min". */
fun formatearMinutos(minutos: Int): String {
    val horas = minutos / 60
    val resto = minutos % 60
    return "${horas}h ${resto}min"
}
