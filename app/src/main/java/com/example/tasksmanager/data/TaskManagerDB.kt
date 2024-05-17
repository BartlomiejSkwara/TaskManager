package com.example.tasksmanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/**
 * TaskManagerDB
 * Klasa reprezentuje implementuję i pełni rolę RoomDatabase,
 * dokonuje konfiguracji i określa encję które będą częścią bazy danych,
 *
 * Posiada companion object który
 * pozwala na zwrócenie obiektu TaskManagerDB a w wypadku gdy nie został on jeszcze stworzony przygotowuje jego nową instancję
 */
@Database(entities = [Task::class], version = 3, exportSchema = false)
abstract class TaskManagerDB: RoomDatabase() {

    /**
     * Metoda przygotowująca TaskDAO
     * @return instancja TaskDao
     */
    abstract fun taskDao(): TaskDAO

    companion object {
        @Volatile
        private var Instance: TaskManagerDB? = null;
        fun getDatabase(context:Context):TaskManagerDB{
             return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TaskManagerDB::class.java, "tasks_database")
                    .fallbackToDestructiveMigration() // wywal po zakończeniu produkcji
                    .build()
                    .also { Instance = it }
            }
        }
    }


}
