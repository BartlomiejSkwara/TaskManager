package com.example.tasksmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

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

    fun getLocalDateTime():LocalDateTime{
        return LocalDate.ofEpochDay(deadlineDay).atTime(LocalTime.ofSecondOfDay(reminderTime.toLong()));
    }
}