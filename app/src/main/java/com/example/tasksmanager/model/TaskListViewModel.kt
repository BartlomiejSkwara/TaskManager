package com.example.tasksmanager.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.tasksmanager.MyApplication
import com.example.tasksmanager.data.Task
import com.example.tasksmanager.data.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TaskListViewModel(
    private  val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle)
    :ViewModel()

{




    val taskListStateFlow: StateFlow<List<Task>> =
        taskRepository.getAllItemsStream().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = emptyList()
        )


    suspend fun completeTask(task: Task){
        taskRepository.deleteItem(task);

    }






    companion object {

        private const val TIMEOUT_MILLIS = 5_000L;

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                return TaskListViewModel(

                    (application as MyApplication).taskRepository,
                    savedStateHandle
                ) as T
            }
        }
    }
}