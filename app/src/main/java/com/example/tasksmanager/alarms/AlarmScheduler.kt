package com.example.tasksmanager.alarms

import com.example.tasksmanager.data.Task

interface AlarmScheduler {
    fun schedule(task: Task);
    fun cancel(task: Task);

}