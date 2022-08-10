package com.example.todolist.ui.mainactivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.TaskListItemBinding
import com.example.todolist.ui.model.TaskUI
import java.util.*


class TasksRecyclerAdapter(
    private val onRemoveClicked: (taskId: Int) -> Unit,
    private val onCheckBoxChanged: (taskId: Int, isChecked: Boolean) -> Unit,
    private val onRowClearedAfterDrag: (tasks: List<TaskUI>) -> Unit
) : ListAdapter<TaskUI, TasksRecyclerAdapter.TaskVH>(TaskDiffUtil()),
    ItemMoveCallback.ItemTouchHelperContract {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskVH {
        return TaskVH.create(parent)
    }

    override fun onBindViewHolder(holder: TaskVH, position: Int) {
        getItem(position)?.let {
            holder.bind(it, onRemoveClicked, onCheckBoxChanged)
        }
    }

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                val list = currentList.toMutableList()
                Collections.swap(list, i, i + 1)
                submitList(list)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                val list = currentList.toMutableList()
                Collections.swap(list, i, i - 1)
                submitList(list)
            }
        }
    }

    override fun onRowClear(myViewHolder: TaskVH?) {
        onRowClearedAfterDrag(currentList)
    }

    class TaskVH private constructor(private val binding: TaskListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        lateinit var onRemoveClicked: (taskId: Int) -> Unit
        lateinit var onCheckBoxChanged: (taskId: Int, isChecked: Boolean) -> Unit

        fun bind(
            task: TaskUI,
            onRemoveClicked: (taskId: Int) -> Unit,
            onCheckBoxChanged: (taskId: Int, isChecked: Boolean) -> Unit
        ) {
            this.onRemoveClicked = onRemoveClicked
            this.onCheckBoxChanged = onCheckBoxChanged
            binding.task = task
            binding.holder = this
        }

        fun onRemoveClickListener(taskId: Int) = onRemoveClicked(taskId)
        fun onCheckBoxChangeListener(taskId: Int, isChecked: Boolean) =
            onCheckBoxChanged(taskId, isChecked)

        companion object {
            fun create(parent: ViewGroup): TaskVH {
                val binding =
                    TaskListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return TaskVH(binding)
            }
        }
    }


}

class TaskDiffUtil : DiffUtil.ItemCallback<TaskUI>() {
    override fun areItemsTheSame(oldItem: TaskUI, newItem: TaskUI): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TaskUI, newItem: TaskUI): Boolean {
        return oldItem == newItem
    }

}

class ItemMoveCallback(private val mAdapter: ItemTouchHelperContract) : ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {}
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(dragFlags, 0)
    }

    override fun onMove(
        recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        mAdapter.onRowMoved(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun clearView(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ) {
        super.clearView(recyclerView, viewHolder)
        if (viewHolder is TasksRecyclerAdapter.TaskVH) {
            val myViewHolder: TasksRecyclerAdapter.TaskVH =
                viewHolder
            mAdapter.onRowClear(myViewHolder)
        }
    }

    interface ItemTouchHelperContract {
        fun onRowMoved(fromPosition: Int, toPosition: Int)
        fun onRowClear(myViewHolder: TasksRecyclerAdapter.TaskVH?)
    }
}






