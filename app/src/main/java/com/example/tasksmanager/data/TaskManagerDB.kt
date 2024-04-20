package com.example.tasksmanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 2, exportSchema = false)
abstract class TaskManagerDB: RoomDatabase() {

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
