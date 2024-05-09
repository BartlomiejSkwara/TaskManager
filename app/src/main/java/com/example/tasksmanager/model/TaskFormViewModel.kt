package com.example.tasksmanager.model

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.tasksmanager.MyApplication
import com.example.tasksmanager.data.Task
import com.example.tasksmanager.data.TaskRepository
import kotlinx.coroutines.flow.firstOrNull
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class TaskFormViewModel(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    var topic by mutableStateOf("")
        private set


    //val datePickerState by muta
    var description by mutableStateOf("")
        private set

    var deadlineTimestamp by mutableStateOf(LocalDate.now())
        private set

    val formattedDeadline by derivedStateOf {
        DateTimeFormatter.ofPattern("MM dd yyyy")
            .format(deadlineTimestamp)
    }

    var reminderHour by mutableStateOf(LocalTime.now())
        private set

    val formattedReminderHour by derivedStateOf {
        //SimpleDateFormat("hh:mm a", Locale.getDefault()).format(reminderHour)
        DateTimeFormatter.ofPattern("HH:mm").format(reminderHour)
//            .format(notificationHour)
    }



    var dateModalVisible by mutableStateOf(false)
        private set

    var reminderHourModalVisible by mutableStateOf(false)
        private set


    fun validateInput():Boolean{
        return topic.isNotBlank() && description.isNotBlank();
    }
    fun changeTopic(string: String){
        topic = string;
    }
    fun changeDescription(string: String){
        description = string;

    }
    fun changeDateModalVisibility(visibility: Boolean){
        dateModalVisible = visibility;
    }

    fun changeReminderHourModalVisibility(visibility: Boolean){
        reminderHourModalVisible = visibility;
    }
    fun changeDeadlineTimestamp(dayMilis: Long){
        // todo może trzeba zmienić to utc
        deadlineTimestamp = LocalDateTime.ofInstant(Instant.ofEpochMilli(dayMilis), ZoneOffset.UTC).toLocalDate();
    };

    fun changeReminderTimestamp(hour: Int, min: Int){
        reminderHour = LocalTime.of(hour,min);

//        deadlineTimestamp.toEpochDay()
            //LocalDateTime.ofInstant(Instant.ofEpochMilli(timeMilis), ZoneId.systemDefault()).toLocalDate();
        //LocalDate.now().
    };


    suspend fun saveItem(): Task? {
        if(validateInput()){
//            var list: List<Task> = emptyList()

//            taskRepository.getAllItemsStream().collect{
//                task->
//                list=task
//            }

            var insertedTask = Task(
                title = topic,
                description = description,
                reminderTime = reminderHour.toSecondOfDay(),
                deadlineDay = deadlineTimestamp.toEpochDay(),
                hue = Random.nextInt(0,360).toFloat()
            );
            val newId = taskRepository.insertItem(
                insertedTask
            );


            return taskRepository.getItemStream(newId).firstOrNull();
//            taskRepository.getAllItemsStream().collect{
//                    task->
//                list=task
//            }
        }

        return null;
    }















    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                return TaskFormViewModel(

                    (application as MyApplication).taskRepository,
                    savedStateHandle
                ) as T
            }
        }
    }

















}