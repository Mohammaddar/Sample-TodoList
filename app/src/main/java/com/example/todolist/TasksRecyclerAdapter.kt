package com.example.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.TaskListItemBinding
import com.example.todolist.ui.model.TaskUI

class TasksRecyclerAdapter(private val onRemoveClicked: (taskId: Int) -> Unit,private val onCheckBoxChanged: (taskId: Int,isChecked:Boolean) -> Unit) :
    ListAdapter<TaskUI, TaskVH>(CoinStatisticsDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskVH {
        return TaskVH.create(parent)
    }

    override fun onBindViewHolder(holder: TaskVH, position: Int) {
        getItem(position)?.let {
            holder.bind(it, onRemoveClicked,onCheckBoxChanged)
        }
    }
}

class TaskVH private constructor(private val binding: TaskListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    lateinit var onRemoveClicked: (taskId: Int) -> Unit
    lateinit var onCheckBoxChanged: (taskId: Int,isChecked:Boolean) -> Unit

    fun bind(
        task: TaskUI,
        onRemoveClicked: (taskId: Int) -> Unit,
        onCheckBoxChanged: (taskId: Int,isChecked:Boolean) -> Unit
    ) {
        this.onRemoveClicked = onRemoveClicked
        this.onCheckBoxChanged = onCheckBoxChanged
        binding.task = task
        binding.holder = this
    }

    fun onRemoveClickListener(taskId: Int) = onRemoveClicked(taskId)
    fun onCheckBoxChangeListener(taskId: Int,isChecked:Boolean) = onCheckBoxChanged(taskId,isChecked)

    companion object {
        fun create(parent: ViewGroup): TaskVH {
            val binding =
                TaskListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TaskVH(binding)
        }
    }
}

class CoinStatisticsDiffUtil : DiffUtil.ItemCallback<TaskUI>() {
    override fun areItemsTheSame(oldItem: TaskUI, newItem: TaskUI): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TaskUI, newItem: TaskUI): Boolean {
        return oldItem == newItem
    }

}