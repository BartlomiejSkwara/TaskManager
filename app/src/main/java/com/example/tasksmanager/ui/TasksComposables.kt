@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.tasksmanager.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.tasksmanager.data.Task
import com.example.tasksmanager.model.TaskFormViewModel
import com.example.tasksmanager.model.TaskListViewModel
import com.example.tasksmanager.ui.theme.onSurfaceVariantDark
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
//@Preview
fun TaskCardList(modifier: Modifier = Modifier, viewModel: TaskListViewModel){



    val taskArray:MutableList<Task> = mutableListOf();
//
    viewModel.taskListStateFlow.collectAsState().value.forEach(
        {t->
        taskArray.add(t)
    })

    val coroutineScope = rememberCoroutineScope()

    val onTaskCheckedChange: (Task) -> Unit = { task ->
        coroutineScope.launch {

            viewModel.completeTask(task);
        }
    }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)


    ){
        items(taskArray){
            TaskCard(it,
                 onTaskCheckedChange
            )

        }
    }
}



@Composable
fun TaskCard(task: Task, onCheckedChange:(Task) -> Unit, modifier: Modifier = Modifier){
    val date = task.getLocalDateTime();
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
                Text(text = task.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                );
                Text(text = task.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall
                );
            }
            Column (modifier = Modifier.weight(2f)){
                Text(text = String.format("%02d.%02d.%d",date.dayOfMonth,date.monthValue,date.year));
                //"${}.${date.monthValue}.${}");

                Text(text = String.format("%02d:%02d", date.hour, date.minute));
            }


            Box(
                modifier = androidx.compose.ui.Modifier
                    .background(Color.hsv(task.hue, 0.885f, 0.718f))
                    .fillMaxHeight()
                    .weight(1f),
                contentAlignment = Alignment.Center,

                ) {
                Checkbox(
                    checked = false,
                    onCheckedChange = {
                        onCheckedChange(task);
                    },

                    )
            }

        }
    }



}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskForm(modifier: Modifier =Modifier, viewModel: TaskFormViewModel) {

    //val viewModel = viewModel<TaskFormViewModel>()

    //val viewModel2: TaskFormViewModel by viewModels {
      //  MyApplication.Factory // Use your custom Factory here
    //}





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

//        Button() {
//            Text(text = "Date Picker")
//        }

        Spacer(modifier = Modifier.size(48.dp))

        OutlinedTextField(
            modifier = Modifier.clickable {viewModel.changeDateModalVisibility(true)},
            value = viewModel.formattedDeadline,
            onValueChange = {},
            label = { Text("Task deadline") },
            leadingIcon = {

                    Icon(imageVector = Icons.Filled.DateRange, contentDescription = null)

            },
            minLines = 1,
            maxLines = 1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledTextColor = onSurfaceVariantDark,
                disabledLeadingIconColor = onSurfaceVariantDark,
                disabledBorderColor = onSurfaceVariantDark,
                disabledLabelColor = onSurfaceVariantDark,
            )    ,
            enabled = false
        )
        DatePickerDialog(openDialog = viewModel.dateModalVisible,
            onCancel = {viewModel.changeDateModalVisibility(false)},
            onOk = {value ->

                viewModel.changeDateModalVisibility(false);
                viewModel.changeDeadlineTimestamp(value);
            }
        );





        Spacer(modifier = Modifier.size(24.dp))

        OutlinedTextField(
            modifier = Modifier.clickable {viewModel.changeReminderHourModalVisibility(true)},
            value = viewModel.formattedReminderHour,
            onValueChange = {},
            label = { Text("Task reminder") },
            leadingIcon = {

                Icon(imageVector = Icons.Filled.Notifications, contentDescription = null)

            },
            minLines = 1,
            maxLines = 1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledTextColor = onSurfaceVariantDark,
                disabledLeadingIconColor = onSurfaceVariantDark,
                disabledBorderColor = onSurfaceVariantDark,
                disabledLabelColor = onSurfaceVariantDark,
            )    ,
            enabled = false
        )
        TimePickerDialogCustom(openDialog = viewModel.reminderHourModalVisible,
            onCancel = {viewModel.changeReminderHourModalVisibility(false)},
            onOk = {h,m ->
                viewModel.changeReminderHourModalVisibility(false);
                viewModel.changeReminderTimestamp(h,m);
            }
        );











    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialogCustom(
    openDialog: Boolean,
    onCancel: () -> Unit,
    onOk: (Int, Int) -> Unit,

    ) {
    val state = rememberTimePickerState()
    val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }



    if (openDialog) {
        TimePickerDialog(
            onCancel = { onCancel() },
            onConfirm = {
                //val cal = Calendar.getInstance()
                //cal.set(Calendar.HOUR_OF_DAY, state.hour)
                //cal.set(Calendar.MINUTE, state.minute)
                //cal.isLenient = false
                //cal.time
                onOk(state.hour, state.minute)
            },
        ) {
            TimePicker(state = state)
        }
    }
}


@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                ) {
                    toggle()
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = onCancel) {
                        Text("Cancel")
                    }
                    TextButton(onClick = onConfirm) {
                        Text("OK")
                    }
                }
            }
        }
    }
}

















@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(openDialog: Boolean,
                     onCancel: ()->Unit,
                     onOk: (date: Long)->Unit
) {
    if (openDialog) {
        val datePickerState = rememberDatePickerState(
            initialDisplayMode = DisplayMode.Picker
        )

        DatePickerDialog(
            onDismissRequest = {
                onCancel()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { onOk(it) }
                    },
    //                    enabled = confirmEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onCancel()
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


