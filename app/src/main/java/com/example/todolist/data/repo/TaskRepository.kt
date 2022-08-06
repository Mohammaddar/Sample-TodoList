package com.example.todolist.data.repo

import android.content.Context
import com.example.todolist.data.local.TaskDatabase
import com.example.todolist.data.local.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository private constructor(private val taskDataBase: TaskDatabase) {

    companion object {
        @Volatile
        private var taskRepository: TaskRepository? = null

        fun getInstance(context: Context): TaskRepository {
            synchronized(this) {
                return taskRepository ?: createTaskRepository(context)
            }
        }

        private fun createTaskRepository(context: Context): TaskRepository {
            val newRepo =
                TaskRepository(TaskDatabase.getInstance(context))
            taskRepository = newRepo
            return newRepo
        }
    }

    fun getAllTasks(): Flow<List<Task>> {
        return taskDataBase.taskDao.getAll()
    }

    suspend fun removeTaskById(id: Int) {
        taskDataBase.taskDao.remove(id)
    }

    suspend fun addNewTask(task: Task) {
        taskDataBase.taskDao.insert(task)
    }

    suspend fun updateTaskState(taskId: Int, isCompleted: Boolean) {
        taskDataBase.taskDao.update(taskId, isCompleted)
    }

    suspend fun clearAllCompletedTasks(){
        taskDataBase.taskDao.clearAllCompleted()
    }
}