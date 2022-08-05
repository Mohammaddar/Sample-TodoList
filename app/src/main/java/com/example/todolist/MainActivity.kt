package com.example.todolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        val adapter = TasksRecyclerAdapter {
        }
        binding.recyclerTasks.apply {
            this.adapter = adapter
            val lManager = LinearLayoutManager(this@MainActivity)
            lManager.orientation = RecyclerView.VERTICAL
            layoutManager=lManager
            addItemDecoration(DividerItemDecoration(context, lManager.orientation))
        }

        val ls = List(4) { "Task number $it" }
        adapter.submitList(ls)
    }
}