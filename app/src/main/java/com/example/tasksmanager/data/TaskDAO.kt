package com.example.tasksmanager.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task:Task)
    @Update()
    suspend fun update(task:Task)
    @Delete()
    suspend fun delete(task:Task)
    @Query("SELECT * from tasks WHERE id = :id")
    fun getItem(id: Int): Flow<Task>
    @Query("SELECT * from tasks")
    fun getAllItems():Flow<List<Task>>

}