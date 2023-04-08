package com.carlosjgr7.todoapp.addtask.domain

import com.carlosjgr7.todoapp.addtask.data.TaskRepository
import com.carlosjgr7.todoapp.addtask.ui.Model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    operator fun invoke(): Flow<List<TaskModel>> = taskRepository.tasks
}