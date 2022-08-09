package com.example.todolist

import android.app.Application
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.local.model.Task
import com.example.todolist.data.repo.TaskRepository
import com.example.todolist.ui.model.TaskUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class MainActivityViewModel(
    private val application: Application,
    private val taskRepository: TaskRepository
) : ViewModel() {

    val tabLayoutState = MutableStateFlow(TaskTabLayoutState.All)

    val tasks = taskRepository.getAllTasks()
        .map { tasks ->
            tasks.map { TaskUI.transformDataModelToUiModel(it) }
        }
        .combine(tabLayoutState) { tasks, state ->
            when (state) {
                TaskTabLayoutState.All -> tasks
                TaskTabLayoutState.Active -> tasks.filter { !it.isCompleted }
                TaskTabLayoutState.Completed -> tasks.filter { it.isCompleted }
            }
        }
        .flowOn(Dispatchers.Default).asLiveData()

    fun onEdtNewTaskAction(text: String, isCompleted: Boolean) {
        viewModelScope.launch {
            taskRepository.addNewTask(Task(text = text, isCompleted = isCompleted))
        }
    }

    fun onBtnNightModeClicked() {
        toggleNightMode(application)
    }

    fun onBtnClearCompletedClicked() {
        viewModelScope.launch {
            taskRepository.clearAllCompletedTasks()
        }
    }

    fun onBtnRemoveTaskClicked(id: Int) {
        viewModelScope.launch {
            taskRepository.removeTaskById(id)
        }
    }

    fun onCBoxIsTaskCompletedChanged(taskId: Int, isChecked: Boolean) {
        viewModelScope.launch {
            taskRepository.updateTaskState(taskId, isChecked)
        }
    }

}