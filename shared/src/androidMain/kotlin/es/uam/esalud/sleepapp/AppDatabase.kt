package es.uam.esalud.sleepapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.uam.esalud.sleepapp.datos.RepositorioSueno

/**
 * La base de datos.
 *
 * `version = 3`: la entidad ha cambiado en la sesión 3 (minutosDespierto) y
 * otra vez en la sesión 4 (usuarioId). Cada cambio de la tabla sube el número
 * de versión del esquema. Como crearRepositorioRoom() usa
 * fallbackToDestructiveMigration(), la base de datos se borra y se recrea
 * vacía. En una aplicación real habría que escribir una migración para no
 * perder los datos de los pacientes.
 */
@Database(entities = [SleepRecordEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sleepDao(): SleepDao
}

/**
 * Abre (o crea, la primera vez) la base de datos y devuelve el repositorio
 * listo para usar.
 *
 * Está aquí, en el módulo `shared`, y no en MainActivity, porque Room es una
 * dependencia de `shared`: el módulo `androidApp` no la ve. Así MainActivity
 * no necesita saber nada de Room, igual que no sabe nada de MediaRecorder y
 * se limita a construir un GrabadorAndroid.
 *
 * fallbackToDestructiveMigration(): si la estructura de la tabla cambia entre
 * versiones de la aplicación, borra la base de datos y la crea de nuevo en
 * lugar de fallar. Es cómodo en desarrollo; en una aplicación real se
 * perderían los datos del usuario.
 */
fun crearRepositorioRoom(context: Context): RepositorioSueno {
    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "sueno_database"
    ).fallbackToDestructiveMigration().build()

    return RepositorioRoom(db.sleepDao())
}
