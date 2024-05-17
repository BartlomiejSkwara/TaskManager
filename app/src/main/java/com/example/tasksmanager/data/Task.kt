package com.example.tasksmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Task
 * obiekt reprezentujący encję tasks
 * @property id - klucz główny
 * @property title - nazwa zadania
 * @property description - opis zadania
 * @property reminderTime - ostateczny termin - godzina
 * @property deadlineDay - ostateczny termin - dzień
 * @property hue - barwa przypisana w aplikacji do zadania
 * @constructor Tworzy obiekt typu Task
 */
@Entity(tableName = "tasks" )
data class Task (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val reminderTime: Int,
    val deadlineDay: Long,
    val hue: Float
    //        reminderHour.toSecondOfDay()
    //        LocalTime.ofSecondOfDay()
    //        LocalDate.ofEpochDay()
){

    /**
     * Get local date time
     * Metoda konwertuje dane dotyczące ostatecznego terminu zadania  w obiekt typu   LocalDateTime
     * @return - zwraca  ostateczny termin jako LocalDateTime
     */
    fun getLocalDateTime():LocalDateTime{
        return LocalDate.ofEpochDay(deadlineDay).atTime(LocalTime.ofSecondOfDay(reminderTime.toLong()));
    }
}