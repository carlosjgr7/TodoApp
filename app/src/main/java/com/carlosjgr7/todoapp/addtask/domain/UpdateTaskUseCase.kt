package com.carlosjgr7.todoapp.addtask.domain

import com.carlosjgr7.todoapp.addtask.data.TaskRepository
import com.carlosjgr7.todoapp.addtask.ui.Model.TaskModel
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(taskModel: TaskModel) {
        taskRepository.update(taskModel)
    }
}