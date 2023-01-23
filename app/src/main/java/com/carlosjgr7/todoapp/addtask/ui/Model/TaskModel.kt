package com.carlosjgr7.todoapp.addtask.ui.Model

data class TaskModel(
    val id: Long = System.currentTimeMillis(),
    val task: String,
    var selected: Boolean =false
)
