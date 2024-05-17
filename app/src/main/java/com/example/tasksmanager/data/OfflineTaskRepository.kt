package com.example.tasksmanager.data

import kotlinx.coroutines.flow.Flow


/**
 * Offline task repository
 * klasa operująca na lokalnej bazie danych poprzez instancję obiektu TaskDAO
 * @property taskDAO - instancja obiektu TaskDAO
 */
class OfflineTaskRepository(private val taskDAO: TaskDAO) : TaskRepository {
    /**
     * Get item stream
     * próbuje pobrać z bazy danych rekord o pewnym id
     * @param id - id szukanego rekordu
     * @return  zwraca strumień danych zawierający szukany rekord lub null
     */
    override fun getItemStream(id: Long): Flow<Task?> =  taskDAO.getItem(id);

    /**
     * Get all items stream
     *  pobiera z bazy danych wszystkie rekordy w tabeli tasks
     * @return zwraca strumień zawierający listę wszystkich rekordów z tabeli tasks
     */
    override fun getAllItemsStream(): Flow<List<Task>> =  taskDAO.getAllItems();

    /**
     * Insert item
     * wstawia do bazy danych do tabeli tasks obiekt typu Task
     * @param task - wstawiany obiekt
     * @return - id obiektu po wstawieniu
     */
    override suspend fun insertItem(task: Task):Long {
        return taskDAO.insert(task)
    };
    /**
     * Delete item
     * usuwa rekord w bazie danych przechowujący obiekt typu Task
     * @param task - obiekt do usunięcia
     */
    override suspend fun deleteItem(task: Task) = taskDAO.delete(task);


    /**
     * Update item
     * aktualizuje obecny stan rekordu obiektu typu task w bazie danych
     * @param task - obiekt reprezentujący wprowadzane zmiany
     */
    override suspend fun updateItem(task: Task) = taskDAO.update(task);
}