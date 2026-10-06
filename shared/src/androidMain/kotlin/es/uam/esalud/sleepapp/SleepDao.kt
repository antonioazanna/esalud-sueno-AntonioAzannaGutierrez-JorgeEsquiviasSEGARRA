package es.uam.esalud.sleepapp

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import es.uam.esalud.sleepapp.datos.SleepRecord
import es.uam.esalud.sleepapp.logica.Hora
import kotlinx.coroutines.flow.Flow

/**
 * La entidad: representa una FILA de la tabla.
 *
 * Comparadla con SleepRecord. Son casi iguales, con una diferencia importante:
 * aquí las horas son números enteros (minutos desde medianoche) en lugar de
 * objetos Hora, porque una base de datos solo guarda tipos simples.
 *
 * Están separadas a propósito: el modelo con el que razona la aplicación no
 * tiene por qué parecerse al que impone la base de datos.
 */
@Entity(tableName = "sleep_records")
data class SleepRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val fechaMillis: Long,
    val inicioMinutos: Int,
    val finMinutos: Int,
    val rutaAudio: String?,
    val calidadPercibida: Int?
)

// Traducción entre los dos modelos. Dado.
// Fijaos en que usa los dos métodos de Hora: desdeMedianoche() para guardar y
// desdeMinutos() para reconstruir el objeto al leer.

fun SleepRecordEntity.aModelo() = SleepRecord(
    id = id,
    fechaMillis = fechaMillis,
    inicio = Hora.desdeMinutos(inicioMinutos),
    fin = Hora.desdeMinutos(finMinutos),
    rutaAudio = rutaAudio,
    calidadPercibida = calidadPercibida
)

fun SleepRecord.aEntidad() = SleepRecordEntity(
    id = id,
    fechaMillis = fechaMillis,
    inicioMinutos = inicio.desdeMedianoche(),
    finMinutos = fin.desdeMedianoche(),
    rutaAudio = rutaAudio,
    calidadPercibida = calidadPercibida
)

/**
 * El DAO: aquí se DECLARAN las operaciones. Room genera la implementación al
 * compilar, y comprueba de paso que vuestro SQL es correcto.
 *
 * La tabla se llama sleep_records y sus columnas son las de la entidad de
 * arriba: id, fechaMillis, inicioMinutos, finMinutos, rutaAudio,
 * calidadPercibida.
 */
@Dao
interface SleepDao {

    @Insert
    suspend fun insertar(registro: SleepRecordEntity)

    // EJERCICIOS 1, 2 y 3
    // Si os atascáis: pistas graduadas en el guion, apartado 4.3.
    //
    // EJERCICIO 1
    // Devolved todos los registros, del más reciente al más antiguo.
    // Pista: SELECT * FROM ... ORDER BY ... DESC
    @Query("SELECT * FROM sleep_records ORDER BY fechaMillis DESC")
    fun observarTodos(): Flow<List<SleepRecordEntity>>

    // EJERCICIO 2
    // Borrad el registro cuyo id coincida con el parámetro.
    // Pista: los parámetros de la función se referencian con dos puntos: :id
    @Query("DELETE FROM sleep_records WHERE id = :id")
    suspend fun borrar(id: Long)

    // EJERCICIO 3 (opcional)
    // Contad cuántos registros hay.
    @Query("SELECT COUNT(*) FROM sleep_records")
    suspend fun contar(): Int
}
