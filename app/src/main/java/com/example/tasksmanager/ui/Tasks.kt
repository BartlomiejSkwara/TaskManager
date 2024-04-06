@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.tasksmanager.ui

import UserTaskInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tasksmanager.model.TaskFormViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime


@Composable
//@Preview
fun TaskCardList( modifier: Modifier = Modifier){


    val taskArray:MutableList<UserTaskInfo> = mutableListOf();
    repeat(15){
        taskArray.add(UserTaskInfo("Wynieś śmieci karambas", "nazbierały się tony śmieci i trzeba je wywalić",
            LocalDateTime.now()));
        taskArray.add(UserTaskInfo("Small", "proste zadanko",
            LocalDateTime.now()));

    }



    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)


    ){
        items(taskArray){
            TaskCard(it)

        }
    }
}



@Composable
@Preview
fun TaskCard(eventInfo: UserTaskInfo = UserTaskInfo("null", "null", LocalDateTime.now(), Color.hsv(35.6f,0.885f,0.718f) ), modifier: Modifier = Modifier){


    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface,

            //containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .size(width = 336.dp, height = 66.dp)
    ) {
        Row ( verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp))
        {
            Column(modifier = Modifier.weight(3f)) {
                Text(text = eventInfo.topic,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                );
                Text(text = eventInfo.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall
                );
            }
            Column (modifier = Modifier.weight(2f)){
                Text(text = "${eventInfo.deadline.dayOfMonth}.${eventInfo.deadline.monthValue}.${eventInfo.deadline.year}",);
                Text(text = "${eventInfo.deadline.hour}:${eventInfo.deadline.minute}");
            }

            Box(
                modifier = androidx.compose.ui.Modifier
                    .background(eventInfo.color)
                    .fillMaxHeight()
                    .weight(1f),
                contentAlignment = Alignment.Center,

                ) {
                Checkbox(
                    checked = false,
                    onCheckedChange = { /* handle checked state */ },

                    )
            }

        }
    }



}



@Composable
@Preview()
fun NewTaskForm(modifier: Modifier =Modifier) {

    val viewModel = viewModel<TaskFormViewModel>()
    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()

    ) {
        OutlinedTextField(value = viewModel.topic,
            onValueChange = { viewModel.changeTopic(it)},
            label = { Text("Task Title") }
        )
        OutlinedTextField(value = viewModel.description,
            onValueChange = {viewModel.changeDescription(it)},
            label = { Text("Task Description") },
            singleLine = false,
            minLines = 3,
            maxLines = 5
        )

        DatePickerDialogModal(openDialog = viewModel.dateModalVisible);

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogModal(openDialog: Boolean) {
    // Decoupled snackbar host state from scaffold state for demo purposes.
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()
    SnackbarHost(hostState = snackState, Modifier)
    // TODO demo how to read the selected date from the state.


    if (openDialog) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled = remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }
        DatePickerDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                //openDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        //openDialog = false
                        snackScope.launch {
                            snackState.showSnackbar(
                                "Selected date timestamp: ${datePickerState.selectedDateMillis}"
                            )
                        }
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        //openDialog.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}