package com.carlosjgr7.todoapp.addtask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.carlosjgr7.todoapp.addtask.ui.Model.TaskModel


@Composable
fun TasksScreen(taskViewModel: TasksViewModel) {

    val showDialog: Boolean by taskViewModel.showDialog.observeAsState(initial = false)

    Box(modifier = Modifier.fillMaxSize()) {
        AddTaskDialog(showDialog,
            onDissmiss = { taskViewModel.onDialogClose() },
            onTaskAdded = {
                taskViewModel.onTaskCreate(it)
                taskViewModel.onDialogClose()
            })
        FabDialog(Modifier.align(Alignment.BottomEnd)) { taskViewModel.showDialogClick() }
        TaskList(taskViewModel)
    }
}

@Composable
fun TaskList(taskViewModel: TasksViewModel) {
    val myTask: List<TaskModel> = taskViewModel.task

    LazyColumn {
        items(myTask, key = { it.id }) {
            itemTask(
                taskModel = it,
                onCheckboxChange = { taskViewModel.onChangeSelected(it) },
                onDeleteTask ={taskViewModel.onDeleteSelected(it) }
            )
        }
    }
}

@Composable
fun itemTask(
    taskModel: TaskModel,
    onCheckboxChange: (TaskModel) -> Unit,
    onDeleteTask: (TaskModel) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                    onDeleteTask(taskModel)
                })
            },
        elevation = 8.dp,
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = taskModel.task, modifier = Modifier
                    .weight(1f)
            )
            Checkbox(
                checked = taskModel.selected,
                onCheckedChange = { onCheckboxChange(taskModel) })

        }
    }

}

@Composable
fun FabDialog(modifier: Modifier, onShowDialog: () -> Unit) {
    FloatingActionButton(
        onClick = {
            onShowDialog()
        }, modifier = modifier.padding(16.dp)
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "")
    }
}


@Composable
fun AddTaskDialog(show: Boolean, onDissmiss: () -> Unit, onTaskAdded: (String) -> Unit) {
    var myTask by remember { mutableStateOf("") }
    if (show) {
        Dialog(onDismissRequest = { onDissmiss() }) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Añade tu tarea",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    value = myTask, onValueChange = { myTask = it }, singleLine = true, maxLines = 1
                )
                Spacer(modifier = Modifier.size(16.dp))
                Button(onClick = {
                    onTaskAdded(myTask)
                    myTask = ""
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Añadir tarea")

                }
            }
        }
    }
}


//Constrain for do Evryting
@Composable
fun TasksConstrainScreen(taskViewModel: TasksViewModel) {
    val showDialog: Boolean by taskViewModel.showDialog.observeAsState(initial = false)

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (fabbtn, dialog) = createRefs()

        AddTaskConstrainDialog(modifier = Modifier.constrainAs(dialog) {
            top.linkTo(parent.top, 16.dp)
            bottom.linkTo(parent.bottom, 16.dp)
            start.linkTo(parent.start, 16.dp)
            end.linkTo(parent.end, 16.dp)
        },
            showDialog,
            onDissmiss = { taskViewModel.onDialogClose() },
            onTaskAdded = {
                taskViewModel.onTaskCreate(it)

            })


        FabConstrainDialog(Modifier.constrainAs(fabbtn) {
            end.linkTo(parent.end, 16.dp)
            bottom.linkTo(parent.bottom, 16.dp)
        }) { taskViewModel.showDialogClick() }


    }

}

@Composable
fun FabConstrainDialog(modifier: Modifier, onShowDialog: () -> Unit) {
    FloatingActionButton(
        onClick = {
            onShowDialog()
        }, modifier = modifier
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "")

    }
}


@Composable
fun AddTaskConstrainDialog(
    modifier: Modifier, show: Boolean, onDissmiss: () -> Unit, onTaskAdded: (String) -> Unit
) {
    var myTask by remember { mutableStateOf("") }
    if (show) {
        Dialog(onDismissRequest = { onDissmiss() }) {
            Column(
                modifier = modifier
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Añade tu tarea",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    value = myTask, onValueChange = { myTask = it }, singleLine = true, maxLines = 1
                )
                Spacer(modifier = Modifier.size(16.dp))
                Button(onClick = {
                    onTaskAdded(myTask)
                    myTask = ""
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Añadir tarea")

                }
            }
        }
    }
}