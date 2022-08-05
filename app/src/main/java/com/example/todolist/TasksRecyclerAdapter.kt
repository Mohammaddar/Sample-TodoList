package com.example.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.TaskListItemBinding

class TasksRecyclerAdapter(private val onClick: (task: String) -> Unit) :
    ListAdapter<String, TaskVH>(CoinStatisticsDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskVH {
        return TaskVH.create(parent)
    }

    override fun onBindViewHolder(holder: TaskVH, position: Int) {
        getItem(position)?.let {
            holder.bind(it, onClick)
        }
    }
}

class TaskVH private constructor(private val binding: TaskListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    lateinit var onClick: (task: String) -> Unit
    fun bind(task: String, onClick: (task: String) -> Unit) {
        this.onClick = onClick
        binding.task=task
    }

    fun onClickListener(task: String) = onClick(task)

    companion object {
        fun create(parent: ViewGroup): TaskVH {
            val binding =
                TaskListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TaskVH(binding)
        }
    }
}

class CoinStatisticsDiffUtil : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}