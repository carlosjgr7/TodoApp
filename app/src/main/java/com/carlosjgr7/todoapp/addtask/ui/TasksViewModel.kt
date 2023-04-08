package com.carlosjgr7.todoapp.addtask.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosjgr7.todoapp.addtask.domain.AddTaskUseCase
import com.carlosjgr7.todoapp.addtask.domain.DeleteTaskUseCase
import com.carlosjgr7.todoapp.addtask.domain.GetTasksUseCase
import com.carlosjgr7.todoapp.addtask.domain.UpdateTaskUseCase
import com.carlosjgr7.todoapp.addtask.ui.Model.TaskModel
import com.carlosjgr7.todoapp.addtask.ui.TaskUiState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    getTasksUseCase: GetTasksUseCase
) : ViewModel() {

    val uiState: StateFlow<TaskUiState> = getTasksUseCase()
        .map(::Succes)
        .catch { Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onTaskCreate(task: String) {
        onDialogClose()
        viewModelScope.launch {
            addTaskUseCase(TaskModel(task = task))
        }

    }

    fun showDialogClick() {
        _showDialog.value = true
    }


    fun onChangeSelected(taskModel: TaskModel) {
        viewModelScope.launch {
            updateTaskUseCase(taskModel.copy(selected = !taskModel.selected))
        }
    }

    fun onDeleteSelected(taskModel: TaskModel) {
        viewModelScope.launch {
            deleteTaskUseCase(taskModel)
        }
    }

}
