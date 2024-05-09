package com.example.tasksmanager.data

import kotlinx.coroutines.flow.Flow

class OfflineTaskRepository(private val taskDAO: TaskDAO) : TaskRepository {
    override fun getItemStream(id: Long): Flow<Task?> =  taskDAO.getItem(id);


    override fun getAllItemsStream(): Flow<List<Task>> =  taskDAO.getAllItems();


    override suspend fun insertItem(task: Task):Long {
        return taskDAO.insert(task)
    };


    override suspend fun deleteItem(task: Task) = taskDAO.delete(task);

    override suspend fun updateItem(task: Task) = taskDAO.update(task);
}