package com.example.tasksmanager.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TaskFormViewModel : ViewModel(){
    var topic by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var deadlineTimestamp by mutableLongStateOf(0L)
        private set

    var dateModalVisible by mutableStateOf(false)
        private set

    fun changeTopic(string: String){
        topic = string;
    }
    fun changeDescription(string: String){
        description = string;
    }
    fun changeDateModalVisibility(visibility: Boolean){
        dateModalVisible = visibility;
    }

    fun changeTimestamp(string: String){

    }



}