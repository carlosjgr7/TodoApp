package com.carlosjgr7.todoapp.addtask.ui

import com.carlosjgr7.todoapp.addtask.ui.Model.TaskModel

sealed interface TaskUiState {
    object Loading:TaskUiState
    data class Error(val throwable: Throwable):TaskUiState
    data class Succes(val tasks:List<TaskModel>):TaskUiState
}