package es.uam.esalud.sleepapp.ui

/** Convierte una cantidad de minutos en un texto del tipo "7h 45min". */
fun formatearMinutos(minutos: Int): String {
    val horas = minutos / 60
    val resto = minutos % 60
    return "${horas}h ${resto}min"
}

/** Redondea un porcentaje a un decimal: 87.51234 -> "87,5 %". */
fun formatearPorcentaje(valor: Double): String {
    val redondeado = (valor * 10).toInt() / 10.0
    return redondeado.toString().replace('.', ',') + " %"
}
