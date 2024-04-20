package com.example.tasksmanager.data
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getItemStream(id: Int): Flow<Task?>
    fun getAllItemsStream(): Flow<List<Task>>
    suspend fun insertItem(task: Task)
    suspend fun deleteItem(task: Task)
    suspend fun updateItem(task: Task)
}