package com.carlosjgr7.todoapp.addtask.domain

import com.carlosjgr7.todoapp.addtask.data.TaskRepository
import com.carlosjgr7.todoapp.addtask.ui.Model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {
       suspend operator fun invoke(taskModel: TaskModel){
           taskRepository.add(taskModel)
       }
}