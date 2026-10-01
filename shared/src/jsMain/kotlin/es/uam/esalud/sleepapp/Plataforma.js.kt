package es.uam.esalud.sleepapp

// El asistente genera los dos targets web: wasmJs para navegadores modernos
// y js como respaldo para los antiguos. Cada uno necesita su `actual`.
actual fun nombrePlataforma(): String = "Navegador (JavaScript)"
