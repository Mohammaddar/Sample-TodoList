package com.example.todolist.ui.mainactivity

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.TaskTabLayoutState
import com.example.todolist.animateNightModeToggle
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
            tasks.map { TaskUI.transformDataModelToUiModel(it) }.sortedBy { it.position }
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
            taskRepository.addNewTask(text = text, isCompleted = isCompleted)
        }
    }

    fun onBtnNightModeClicked(mainActivity: MainActivity) {
        animateNightModeToggle(
            application, mainActivity,
            mainActivity.binding.btnNightMode.x + mainActivity.binding.btnNightMode.width / 2,
            mainActivity.binding.btnNightMode.y + mainActivity.binding.btnNightMode.height / 2
        )
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

    fun updateAllTaskPositions(tasks: List<TaskUI>) {
        viewModelScope.launch {
            taskRepository.updateAllTasksPositions(tasks.mapIndexed { index, taskUI -> index to taskUI.id })
        }
    }

}