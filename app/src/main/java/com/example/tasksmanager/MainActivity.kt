@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.tasksmanager

import android.Manifest
import android.app.Notification
import android.content.Context
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.runtime.*

import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tasksmanager.alarms.AndroidAlarmScheduler
import com.example.tasksmanager.ui.theme.TasksManagerTheme
import com.example.ui.tasksmanager.StructureFormTask
import com.example.ui.tasksmanager.StructureSectionCalendar
import com.example.ui.tasksmanager.StructureSectionProjects
import com.example.ui.tasksmanager.StructureSectionTask


/**
 * Main activity
 * główna aktywność
 */
class MainActivity : ComponentActivity() {
    /**
     * On create
     * przygotowania w ramach rozpoczęcia działania aktywności takie jak przekazanie @Composable do renderowania i aktywacja nawigacji
     * @param savedInstanceState - poprzedni stan aktywności lub null
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TasksManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}


/**
 * Navigation
 * metoda odpowiedzialna za nawigację między widokami
 * TworzyNavHostController  i NavHost z określonymi routami do poszczególnych widoków
 * @param modifier - modyfikatory które zostaną zaaplikowane do  do @Composable
 */
@Composable
@Preview
fun Navigation( modifier: Modifier = Modifier) {

    val taskManagerNavController = rememberNavController();

    NavHost(
        navController = taskManagerNavController,
        startDestination = TaskOrganizerScreen.Tasks.name,
        //modifier = Modifier.padding(modifiedPadding)
    ){

        composable(route = TaskOrganizerScreen.Tasks.name){
           StructureSectionTask(taskManagerNavController)
            
        }
        composable(route = TaskOrganizerScreen.TaskForm.name){
            StructureFormTask(taskManagerNavController)
        }
        composable(route = TaskOrganizerScreen.Projects.name){
            StructureSectionProjects(taskManagerNavController)
        }
        composable(route = TaskOrganizerScreen.Calendar.name){
            StructureSectionCalendar(taskManagerNavController)
        }
//        composable(route = TaskOrganizerScreen.TaskForm.name){
//            StructureFormTask(navController)
//        }
    }

}







