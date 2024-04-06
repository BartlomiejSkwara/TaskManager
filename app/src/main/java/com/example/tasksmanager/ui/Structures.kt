package com.example.ui.tasksmanager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tasksmanager.Footer
import com.example.tasksmanager.FooterState
import com.example.tasksmanager.MyHeader
import com.example.tasksmanager.ui.NewTaskForm
import com.example.tasksmanager.ui.TaskCardList
import com.example.tasksmanager.TaskOrganizerScreen
import com.example.tasksmanager.ui.theme.TasksManagerTheme


@Composable
@Preview
fun StructureSectionTask(navController: NavHostController = rememberNavController()){
    UniversalStructure(
        innerContent = {modifier -> TaskCardList(modifier) },
        headerSectionTitle = " - Tasks List",
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
        headerSectionTitle = " - Projects List",
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
        headerSectionTitle = " - Calendar",
        onFabClick = {},
        fabIcon = Icons.Filled.Add,
        footerState = FooterState.Calendar,
        navController = navController

    )
}
@Composable
@Preview
fun StructureFormTask(navController: NavHostController = rememberNavController()){
    UniversalStructure(
        innerContent = {modifier -> NewTaskForm(modifier) },
        headerSectionTitle = " - Adding Task",
        headerNavigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
        onHeaderNavIconClick = {
            navController.navigate(route = TaskOrganizerScreen.Tasks.name);


        },
        onFabClick = {},
        fabIcon = Icons.Filled.Done,
        footerState = FooterState.Hidden,
        navController = navController

    )
}


@Composable
fun UniversalStructure(
    innerContent: @Composable (Modifier) -> Unit,
    footerState: FooterState = FooterState.Hidden,

    /// TODO FIX  icon not showing up
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