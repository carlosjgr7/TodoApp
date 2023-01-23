package com.carlosjgr7.todoapp.addtask.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.carlosjgr7.todoapp.addtask.ui.Model.TaskModel
import kotlinx.coroutines.selects.select
import javax.inject.Inject

class TasksViewModel @Inject constructor() : ViewModel() {

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    private val _task = mutableStateListOf<TaskModel>()
    val task:List<TaskModel> = _task


    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onTaskCreate(task: String) {
        Log.i(this.javaClass.name, task)
        _task.add(TaskModel(task=task))
        onDialogClose()

    }

    fun showDialogClick() {
        _showDialog.value = true
    }


    fun onChangeSelected(taskModel: TaskModel) {
        val index = _task.indexOf(taskModel)
        _task[index] = _task[index].let { it.copy(selected = !it.selected) }
    }

    fun onDeleteSelected(taskModel: TaskModel) {
        val task = _task.find { it.id == taskModel.id }
        _task.remove(task)
    }

}
