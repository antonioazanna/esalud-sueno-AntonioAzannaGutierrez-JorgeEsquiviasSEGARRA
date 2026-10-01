package es.uam.esalud.sleepapp

/**
 * Demostración mínima del mecanismo `expect` / `actual`.
 *
 * `expect` declara QUÉ existe, sin decir CÓMO se implementa.
 * Cada plataforma aporta su `actual` correspondiente.
 *
 * Buscad las tres implementaciones en:
 *   - androidMain/.../Plataforma.android.kt
 *   - desktopMain/.../Plataforma.desktop.kt
 *   - wasmJsMain/.../Plataforma.wasmJs.kt
 */
expect fun nombrePlataforma(): String
