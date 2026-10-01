package es.uam.esalud.sleepapp

actual fun nombrePlataforma(): String =
    "Escritorio (" + System.getProperty("os.name") + ")"
