package com.carlosjgr7.todoapp.addtask.ui.Model

data class TaskModel(
    val id: Int = System.currentTimeMillis().hashCode(),
    val task: String,
    var selected: Boolean =false
)
