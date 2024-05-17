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

/**
 * Task form view model
 * view model dla formularza
 * @property taskRepository - repozytorium pozwalające  wykonywanie operacji CRUD  na tabeli tasks
 * jest automatycznie wstrzykiwana a proces wstrzykiwania jest zdefiniowany w companion object
 *
 */
class TaskFormViewModel(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    /**
     * Topic
     * @param topic - wpisana do formularza wartość reprezentująca nazwę zadania
     */
    var topic by mutableStateOf("")
        private set
    /**
     * description
     * @param description - wpisana do formularza wartość reprezentująca opis zadania
     */
    var description by mutableStateOf("")
        private set

    /**
     * Deadline timestamp
     * @param deadlineTimestamp - wybrana w formularzu wartość reprezentująca datę ostatecznego terminu zadania
     */
    var deadlineTimestamp by mutableStateOf(LocalDate.now())
        private set

    /**
     * Formatted Deadline
     * @param formattedDeadline - sformatowana data
     */
    val formattedDeadline by derivedStateOf {
        DateTimeFormatter.ofPattern("MM dd yyyy")
            .format(deadlineTimestamp)
    }

    /**
     * Reminder hour
     * @param reminderHour - wybrana w formularzu wartość reprezentująca godzinę ostatecznego terminu zadania
     */
    var reminderHour by mutableStateOf(LocalTime.now())
        private set


    /**
     * Reminder hour
     * @param formattedReminderHour - sformatowana godzina
     */
    val formattedReminderHour by derivedStateOf {
        //SimpleDateFormat("hh:mm a", Locale.getDefault()).format(reminderHour)
        DateTimeFormatter.ofPattern("HH:mm").format(reminderHour)
//            .format(notificationHour)
    }

    /**
     * Date modal visible
     * @param dateModalVisible - określa czy widoczny jest modal do wyboru daty
     */
    var dateModalVisible by mutableStateOf(false)
        private set

    /**
     * Reminder hour modal visible
     * @param reminderHourModalVisible - określa czy widoczny jest modal do wyboru godziny
     */
    var reminderHourModalVisible by mutableStateOf(false)
        private set


    /**
     * Validate Input
     * metoda określa czy dane wprowadzone do formularza są poprawne
     * @return wynik validacji
     */
    fun validateInput():Boolean{
        return topic.isNotBlank() && description.isNotBlank();
    }

    /**
     * Change Topic
     * metoda zmienia obecny stan pola topic
     * @param string
     */
    fun changeTopic(string: String){
        if(string.length>50)
            topic = string.subSequence(0,49).toString();
        else
            topic = string;
    }

    /**
     * Change Description
     * metoda zmienia obecny stan pola description
     * @param string
     */
    fun changeDescription(string: String){
        if(string.length>180)
            description = string.subSequence(0,179).toString();
        else
            description = string;

    }

    /**
     * metoda zmienia widoczność modalu do wyboru daty
     * @param visibility
     */
    fun changeDateModalVisibility(visibility: Boolean){
        dateModalVisible = visibility;
    }

    /**
     * metoda zmienia widoczność modalu do wyboru godziny
     * @param visibility
     */
    fun changeReminderHourModalVisibility(visibility: Boolean){
        reminderHourModalVisible = visibility;
    }

    /**
     * metoda zmienia obecnie wybraną datę terminu końcowego
     * @param dayMilis -  data określona przez liczbę milisekun od północy 01.01.1970
     */
    fun changeDeadlineTimestamp(dayMilis: Long){
        // todo może trzeba zmienić to utc
        deadlineTimestamp = LocalDateTime.ofInstant(Instant.ofEpochMilli(dayMilis), ZoneOffset.UTC).toLocalDate();
    };

    /**
     * metoda zmienia obecnie wybraną godzinę terminu końcowego
     * @param hour
     * @param min
     */
    fun changeReminderTimestamp(hour: Int, min: Int){
        reminderHour = LocalTime.of(hour,min);

//        deadlineTimestamp.toEpochDay()
            //LocalDateTime.ofInstant(Instant.ofEpochMilli(timeMilis), ZoneId.systemDefault()).toLocalDate();
        //LocalDate.now().
    };


    /**
     * funkcja tworzy korzystając z określonych w formularzu pól obiekt typu Task
     * i zapisuje go do bazy danych
     * @return - null albo jeśli zapis się powiódł instancję zapisanego obiektu
     */
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