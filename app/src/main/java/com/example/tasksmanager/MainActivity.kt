@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.tasksmanager



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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tasksmanager.ui.theme.TasksManagerTheme
import com.example.ui.tasksmanager.StructureFormTask
import com.example.ui.tasksmanager.StructureSectionCalendar
import com.example.ui.tasksmanager.StructureSectionProjects
import com.example.ui.tasksmanager.StructureSectionTask

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TasksManagerTheme {
                // A surface container using the 'background' color from the theme
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








@Composable
@Preview
fun Navigation( modifier: Modifier = Modifier) {

    val taskManagerNavController = rememberTaskManagerNavController();

    NavHost(
        navController = taskManagerNavController.navController,
        startDestination = TaskOrganizerScreen.Tasks.name,
        //modifier = Modifier.padding(modifiedPadding)
    ){

        composable(route = TaskOrganizerScreen.Tasks.name){
           StructureSectionTask(taskManagerNavController.navController)

        }
        composable(route = TaskOrganizerScreen.TaskForm.name){
            StructureFormTask(taskManagerNavController.navController)
        }
        composable(route = TaskOrganizerScreen.Projects.name){
            StructureSectionProjects(taskManagerNavController.navController)
        }
        composable(route = TaskOrganizerScreen.Calendar.name){
            StructureSectionCalendar(taskManagerNavController.navController)
        }
//        composable(route = TaskOrganizerScreen.TaskForm.name){
//            StructureFormTask(navController)
//        }
    }

}







@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyHeader(sectionName:String, navigationIconVector: ImageVector? = null, onHeaderNavIconClick: (() -> Unit)? = null, modifier: Modifier = Modifier ) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.app_title)+sectionName,
                    style = MaterialTheme.typography.titleLarge
                    )

            }
        },
        navigationIcon = {
            if (navigationIconVector != null && onHeaderNavIconClick != null) {
                IconButton(onClick = onHeaderNavIconClick ) {
                    Icon(
                        imageVector = navigationIconVector,
                        contentDescription = ""
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Footer(footerState: FooterState, onClick1: () -> Unit, onClick2: () -> Unit, onClick3: () -> Unit, modifier: Modifier = Modifier ) {
    //var selected by remember
    NavigationBar (
        modifier = Modifier
            .height(60.dp)
    ) {
        NavigationBarItem(
            icon = {
//                BadgedBox(
//                    badge = {
//                        Badge {
//                            val badgeNumber = "8"
//                            Text(
//                                badgeNumber,
//                                modifier = Modifier.semantics {
//                                    contentDescription = "$badgeNumber new notifications"
//                                }
//                            )
//                        }
//                    }) {
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = stringResource(R.string.navBar1)
                    )
                //}

            },
            selected = footerState==FooterState.Task,
            onClick = {if(footerState!=FooterState.Task)onClick1()}
        )
        NavigationBarItem(
            icon = {
//                BadgedBox(
//                    badge = {
//                        Badge {
//                            val badgeNumber = "8"
//                            Text(
//                                badgeNumber,
//                                modifier = Modifier.semantics {
//                                    contentDescription = "$badgeNumber new notifications"
//                                }
//                            )
//                        }
//                    }) {
                    Icon(
                        Icons.AutoMirrored.Filled.List,
                        contentDescription = stringResource(R.string.navBar2)
                    )
                //}

            },
            selected = footerState==FooterState.Projects,
            onClick = {if(footerState!=FooterState.Projects)onClick2()}

        );
        NavigationBarItem(
            icon = {
//                BadgedBox(
//                    badge = {
//                        Badge {
//                            val badgeNumber = "8"
//                            Text(
//                                badgeNumber,
//                                modifier = Modifier.semantics {
//                                    contentDescription = "$badgeNumber new notifications"
//                                }
//                            )
//                        }
//                    }) {
                    Icon(
                        Icons.Filled.DateRange,
                        contentDescription = stringResource(R.string.navBar3)
                    )
                //}

            },
            selected = footerState==FooterState.Calendar,
            onClick = {if(footerState!=FooterState.Calendar)onClick3()}

        );
    }
}

