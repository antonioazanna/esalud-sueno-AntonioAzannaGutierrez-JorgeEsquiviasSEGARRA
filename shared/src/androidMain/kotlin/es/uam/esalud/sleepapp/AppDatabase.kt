package es.uam.esalud.sleepapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.uam.esalud.sleepapp.datos.RepositorioSueno

/**
 * La base de datos: une las entidades con sus DAO.
 *
 * `version = 1` es la versión del esquema. Al cambiar la entidad habría que
 * subirla, o borrar y recrear la base de datos, que es lo que hace
 * crearRepositorioRoom(), más abajo.
 */
@Database(entities = [SleepRecordEntity::class], version = 1)
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
