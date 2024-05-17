package com.example.tasksmanager.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


/**
 * Task d a o
 *  interfejs opisujący jakiego typu operacji można dokonać na tabeli tasks
 *  implementacja funkcji tego interfejsu jest dokonywana automatycznie podczas kompilacji
 */
@Dao
interface TaskDAO {
    /**
     * Insert
     * wstawia do bazy danych do tabeli tasks obiekt typu Task
     * @param task - wstawiany obiekt
     * @return - id obiektu po wstawieniu
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task:Task): Long

    /**
     * Update
     * aktualizuje obecny stan rekordu obiektu typu task w bazie danych
     * @param task - obiekt reprezentujący wprowadzane zmiany
     */
    @Update()
    suspend fun update(task:Task)

    /**
     * Delete
     * usuwa rekord w bazie danych przechowujący obiekt typu Task
     * @param task - obiekt do usunięcia
     */
    @Delete()
    suspend fun delete(task:Task)

    /**
     * Get item
     * próbuje pobrać z bazy danych rekord o pewnym id
     * @param id - id szukanego rekordu
     * @return  zwraca strumień danych zawierający szukany rekord lub null
     */
    @Query("SELECT * from tasks WHERE id = :id")
    fun getItem(id: Long): Flow<Task>

    /**
     * Get all items
     *  pobiera z bazy danych wszystkie rekordy w tabeli tasks
     * @return zwraca strumień zawierający listę wszystkich rekordów z tabeli tasks
     */
    @Query("SELECT * from tasks")
    fun getAllItems():Flow<List<Task>>

}