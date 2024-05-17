package com.example.ui.tasksmanager

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tasksmanager.FooterState
import com.example.tasksmanager.R
import com.example.tasksmanager.ui.NewTaskForm
import com.example.tasksmanager.ui.TaskCardList
import com.example.tasksmanager.TaskOrganizerScreen
import com.example.tasksmanager.model.TaskFormViewModel
import com.example.tasksmanager.model.TaskListViewModel
import com.example.tasksmanager.ui.theme.TasksManagerTheme
import kotlinx.coroutines.launch
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tasksmanager.alarms.AndroidAlarmScheduler
import com.example.tasksmanager.ui.theme.TasksManagerTheme
import com.example.ui.tasksmanager.StructureFormTask
import com.example.ui.tasksmanager.StructureSectionCalendar
import com.example.ui.tasksmanager.StructureSectionProjects
import com.example.ui.tasksmanager.StructureSectionTask


/**
 * uniwersalna struktura dostoswana do widoku listy zadań
 *
 * @param navController - instancja nav controllera
 */
@Composable
@Preview
fun StructureSectionTask(navController: NavHostController = rememberNavController()){
    val viewModel: TaskListViewModel = viewModel(factory = TaskListViewModel.Factory);

    UniversalStructure(
        innerContent = {modifier -> TaskCardList(modifier,viewModel) },
        headerSectionTitle = stringResource(R.string.titleTasksList),
        onFabClick = {
            navController.navigate(route = TaskOrganizerScreen.TaskForm.name);
        },
        fabIcon = Icons.Filled.Add,
        footerState = FooterState.Task,
        navController = navController


    )
}
@Composable
@Preview
fun StructureSectionProjects(navController: NavHostController = rememberNavController()){
    UniversalStructure(
        innerContent = {},
        headerSectionTitle = stringResource(R.string.titleProjectsList),
        onFabClick = {},
        fabIcon = Icons.Filled.Add,
        footerState = FooterState.Projects,
        navController = navController

    )
}
@Composable
@Preview
fun StructureSectionCalendar(navController: NavHostController = rememberNavController()){
    UniversalStructure(
        innerContent = {},
        headerSectionTitle = stringResource(R.string.titleCalendar),
        onFabClick = {},
        fabIcon = Icons.Filled.Add,
        footerState = FooterState.Calendar,
        navController = navController

    )
}

/**
 * uniwersalna struktura dostoswana do widoku formularza zadań
 *
 * @param navController - instancja nav controllera
 */
@Composable
@Preview
fun StructureFormTask(navController: NavHostController = rememberNavController()){

    val viewModel: TaskFormViewModel = viewModel(factory = TaskFormViewModel.Factory);
    val coroutineScope = rememberCoroutineScope()




    val  context  = LocalContext.current

    var hasNotificationPermission by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(context,
                    Manifest.permission.POST_NOTIFICATIONS)
                        == PackageManager.PERMISSION_GRANTED

            )
        } else mutableStateOf(true)
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
                isGranted->hasNotificationPermission = isGranted;
//                            if(!isGranted){
//                                shouldShowRequestPermissionRationale()
//                            }
        });


    val scheduler = AndroidAlarmScheduler(context);

    UniversalStructure(
        innerContent = {modifier -> NewTaskForm(modifier, viewModel = viewModel) },
        headerSectionTitle = stringResource(R.string.titleAddingTask),
        headerNavigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
        onHeaderNavIconClick = {
            navController.navigate(route = TaskOrganizerScreen.Tasks.name);


        },
        onFabClick = {
            coroutineScope.launch {
                val task = viewModel.saveItem()
                if(task!=null)
                {

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);

                    }
                    scheduler.schedule(task);
                    //task?.let (scheduler::schedule);
                    navController.navigate(route = TaskOrganizerScreen.Tasks.name);




                }
            }
        },
        fabIcon = Icons.Filled.Done,
        footerState = FooterState.Hidden,
        navController = navController

    )
}


/**
 * funkcja służy jako uniwersalny szablon dla widoków używanych w aplikacji,
 * pozwala na zmianę kluczowych elementów bez zmiany ogólnej struktury widoku
 * @param innerContent - główna zawartość specyficzna dla konkretnego widoku
 * @param footerState - określa czy ma być widoczna stopka
 * @param headerNavigationIcon - określa jaka ikonka ma widnieć w lewym górnym rogu ekranu
 * @param onHeaderNavIconClick - lambda określająca co ma się wydarzyć po kliknięciu ikonki w lewym górnym rogu ekranu
 * @param headerSectionTitle - określa jaki napis ma widnieć w nagłówku
 * @param fabIcon - określa jaką ikonkę ma zwierać FAB
 * @param onFabClick - lambda określająca co ma się wydarzyć po kliknięciu w FAB
 * @param navController - instancja nav controllera
 */
@Composable
fun UniversalStructure(
    innerContent: @Composable (Modifier) -> Unit,
    footerState: FooterState = FooterState.Hidden,

    headerNavigationIcon: ImageVector? = null,
    onHeaderNavIconClick: (() -> Unit)? = null,
    headerSectionTitle: String,
    fabIcon: ImageVector? = null,
    onFabClick: (() -> Unit)? = null,
    navController: NavHostController

    ){



    TasksManagerTheme {
        Scaffold(
            topBar = {
                if (headerNavigationIcon!=null)
                    MyHeader(headerSectionTitle,headerNavigationIcon,onHeaderNavIconClick);
                else
                    MyHeader(headerSectionTitle);
            },
            bottomBar = {
                if (footerState!= FooterState.Hidden) {
                    Footer(footerState,
                        { navController.navigate(route = TaskOrganizerScreen.Tasks.name) },
                        { navController.navigate(route = TaskOrganizerScreen.Projects.name) },
                        { navController.navigate(route = TaskOrganizerScreen.Calendar.name) })
                }
            },
            content = {
                innerPadding->
                // Main content area
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),

                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    val modifiedPadding = calculateModifiedPadding(innerPadding,
                        LocalLayoutDirection.current);
                    innerContent(Modifier.padding(modifiedPadding));
                }

            },
            floatingActionButton = {
                if (onFabClick != null) {
                    FloatingActionButton(
                        onClick = onFabClick,
                    ) {
                        if (fabIcon != null) {
                            Icon(imageVector = fabIcon,
                                contentDescription = ""
                            )
                        }
                    }
                }
            }
        )

    }

}



fun calculateModifiedPadding(startingPadding: PaddingValues, layoutDir:LayoutDirection): PaddingValues {
    return PaddingValues(
        top = startingPadding.calculateTopPadding() + 8.dp,
        start = startingPadding.calculateStartPadding(layoutDir) + 16.dp,
        end = startingPadding.calculateEndPadding(layoutDir) + 16.dp,
        bottom = startingPadding.calculateBottomPadding() + 8.dp
    );
}


/**
 * uniwersalny nagłówek
 * @param sectionName - napis w nagłówku
 * @param navigationIconVector - ikonka w lewym rogu nagłówka
 * @param onHeaderNavIconClick - lambda wykonywane po wciśnięciu ikonki w lewym rogu nagłówka
 * @param modifier - modyfikatory dla Composable funkcji
 */
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


/**
 * uniwersalna stopka
 *
 * @param footerState - stan widoczności stopki
 * @param onClick1 - lambda wykonywana po wciśnięciu przycisku 1
 * @param onClick2 - lambda wykonywana po wciśnięciu przycisku 2
 * @param onClick3 - lambda wykonywana po wciśnięciu przycisku 3
 * @param modifier - modyfikatory dla Composable funkcji
 */
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
//        NavigationBarItem(
//            icon = {
////                BadgedBox(
////                    badge = {
////                        Badge {
////                            val badgeNumber = "8"
////                            Text(
////                                badgeNumber,
////                                modifier = Modifier.semantics {
////                                    contentDescription = "$badgeNumber new notifications"
////                                }
////                            )
////                        }
////                    }) {
//                Icon(
//                    Icons.AutoMirrored.Filled.List,
//                    contentDescription = stringResource(R.string.navBar2)
//                )
//                //}
//
//            },
//            selected = footerState==FooterState.Projects,
//            onClick = {if(footerState!=FooterState.Projects)onClick2()}
//
//        );
//        NavigationBarItem(
//            icon = {
////                BadgedBox(
////                    badge = {
////                        Badge {
////                            val badgeNumber = "8"
////                            Text(
////                                badgeNumber,
////                                modifier = Modifier.semantics {
////                                    contentDescription = "$badgeNumber new notifications"
////                                }
////                            )
////                        }
////                    }) {
//                Icon(
//                    Icons.Filled.DateRange,
//                    contentDescription = stringResource(R.string.navBar3)
//                )
//                //}
//
//            },
//            selected = footerState==FooterState.Calendar,
//            onClick = {if(footerState!=FooterState.Calendar)onClick3()}
//
//        );
    }
}

