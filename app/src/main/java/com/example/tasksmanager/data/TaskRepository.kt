package com.example.tasksmanager.data
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getItemStream(id: Long): Flow<Task?>
    fun getAllItemsStream(): Flow<List<Task>>
    suspend fun insertItem(task: Task):Long
    suspend fun deleteItem(task: Task)
    suspend fun updateItem(task: Task)
}