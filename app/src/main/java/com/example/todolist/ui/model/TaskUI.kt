package com.example.todolist.ui.model

import com.example.todolist.data.local.model.Task

data class TaskUI(
    var id: Int,
    var text: String?,
    var isCompleted: Boolean,
    var position: Int
) {
    companion object {
        fun transformDataModelToUiModel(task: Task): TaskUI {
            return TaskUI(
                task.id,
                task.text,
                task.isCompleted,
                task.position
            )
        }
    }
}