package com.example.todolist.ui.mainactivity

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.data.repo.TaskRepository

class MainActivityViewModelFactory(
    private val application: Application,
    private val taskRepository: TaskRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(application,taskRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

