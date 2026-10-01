package es.uam.esalud.sleepapp

import es.uam.esalud.sleepapp.logica.Hora
import es.uam.esalud.sleepapp.logica.duracionEnMinutos
import es.uam.esalud.sleepapp.logica.eficiencia
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LogicaSuenoTest {

    @Test
    fun noche_normal_cruzando_medianoche() {
        val minutos = duracionEnMinutos(
            inicio = Hora(23, 30),
            fin = Hora(7, 15)
        )
        assertEquals(7 * 60 + 45, minutos)
    }

    @Test
    fun siesta_sin_cruzar_medianoche() {
        val minutos = duracionEnMinutos(
            inicio = Hora(15, 0),
            fin = Hora(16, 30)
        )
        assertEquals(90, minutos)
    }

    @Test
    fun dormir_justo_desde_medianoche() {
        val minutos = duracionEnMinutos(
            inicio = Hora(0, 0),
            fin = Hora(8, 0)
        )
        assertEquals(480, minutos)
    }

    @Test
    fun eficiencia_como_porcentaje() {
        // 7 horas dormidas de 8 horas en cama
        assertEquals(87.5, eficiencia(minutosDormido = 420, minutosEnCama = 480), 0.01)
    }

    @Test
    fun eficiencia_perfecta() {
        assertEquals(100.0, eficiencia(minutosDormido = 480, minutosEnCama = 480), 0.01)
    }

    @Test
    fun eficiencia_con_cero_minutos_en_cama_no_revienta() {
        // Caso límite: dato clínico incompleto.
        // Decidid qué debe pasar y justificadlo en la memoria.
        assertFailsWith<IllegalArgumentException> {
            eficiencia(minutosDormido = 0, minutosEnCama = 0)
        }
    }

    @Test
    fun una_hora_invalida_no_se_puede_construir() {
        assertFailsWith<IllegalArgumentException> {
            Hora(25, 0)
        }
    }
}
