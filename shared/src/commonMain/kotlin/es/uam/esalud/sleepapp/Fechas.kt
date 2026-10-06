package es.uam.esalud.sleepapp

import es.uam.esalud.sleepapp.logica.Hora

/**
 * Milisegundos transcurridos desde el 1 de enero de 1970.
 *
 * Es `expect` porque cada plataforma tiene su propia forma de consultar el
 * reloj del sistema. Buscad las cuatro implementaciones `actual`.
 */
expect fun ahoraEnMillis(): Long

/** La hora actual del día, sin fecha. */
expect fun horaActual(): Hora
