package es.uam.esalud.sleepapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform