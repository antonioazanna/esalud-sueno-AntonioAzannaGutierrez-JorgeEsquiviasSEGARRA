package es.uam.esalud.sleepapp

import android.os.Build

actual fun nombrePlataforma(): String = "Android ${Build.VERSION.RELEASE}"
