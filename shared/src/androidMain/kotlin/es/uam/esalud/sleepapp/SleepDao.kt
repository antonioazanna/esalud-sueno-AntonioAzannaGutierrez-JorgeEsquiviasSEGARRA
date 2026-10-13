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
 * Gana una columna en la sesión 3: minutosDespierto.
 */
@Entity(tableName = "sleep_records")
data class SleepRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val fechaMillis: Long,
    val inicioMinutos: Int,
    val finMinutos: Int,
    val rutaAudio: String?,
    val calidadPercibida: Int?,
    val minutosDespierto: Int
)

fun SleepRecordEntity.aModelo() = SleepRecord(
    id = id,
    fechaMillis = fechaMillis,
    inicio = Hora.desdeMinutos(inicioMinutos),
    fin = Hora.desdeMinutos(finMinutos),
    rutaAudio = rutaAudio,
    calidadPercibida = calidadPercibida,
    minutosDespierto = minutosDespierto
)

fun SleepRecord.aEntidad() = SleepRecordEntity(
    id = id,
    fechaMillis = fechaMillis,
    inicioMinutos = inicio.desdeMedianoche(),
    finMinutos = fin.desdeMedianoche(),
    rutaAudio = rutaAudio,
    calidadPercibida = calidadPercibida,
    minutosDespierto = minutosDespierto
)

@Dao
interface SleepDao {

    @Insert
    suspend fun insertar(registro: SleepRecordEntity)

    @Query("SELECT * FROM sleep_records ORDER BY fechaMillis DESC")
    fun observarTodos(): Flow<List<SleepRecordEntity>>

    @Query("DELETE FROM sleep_records WHERE id = :id")
    suspend fun borrar(id: Long)

    @Query("SELECT COUNT(*) FROM sleep_records")
    suspend fun contar(): Int
}
