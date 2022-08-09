package com.example.todolist

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.data.repo.TaskRepository
import com.example.todolist.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainActivityViewModel
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
        checkNightMode(applicationContext)
        setupTasksRecycler()
        setupTasksTabLayout()

    }

    private fun initialize() {
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        binding.lifecycleOwner=this

        viewModel = ViewModelProvider(
            this,
            MainActivityViewModelFactory(
                application,
                TaskRepository.getInstance(this)
            )
        )[MainActivityViewModel::class.java]
        binding.viewModel = viewModel

        binding.edtNewTask.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.onEdtNewTaskAction(
                    binding.edtNewTask.text.toString(),
                    binding.checkboxNewTaskIsCompleted.isChecked
                )
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun setupTasksTabLayout() {
        binding.tabLayoutTasks.apply {
            addTab(newTab().setText("All"))
            addTab(newTab().setText("Active"))
            addTab(newTab().setText("Completed"))

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.text) {
                        "All" -> viewModel.tabLayoutState.value = TaskTabLayoutState.All
                        "Active" -> viewModel.tabLayoutState.value = TaskTabLayoutState.Active
                        "Completed" -> viewModel.tabLayoutState.value = TaskTabLayoutState.Completed
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
            getTabAt(viewModel.tabLayoutState.value.ordinal)?.select()
        }
    }

    private fun setupTasksRecycler() {
        val adapter = TasksRecyclerAdapter(
            onRemoveClicked = { taskId ->
                viewModel.onBtnRemoveTaskClicked(taskId)
            },
            onCheckBoxChanged = { taskId, isChecked ->
                viewModel.onCBoxIsTaskCompletedChanged(taskId,isChecked)
            }
        )

        binding.recyclerTasks.apply {
            this.adapter = adapter
            val lManager = LinearLayoutManager(this@MainActivity)
            lManager.orientation = RecyclerView.VERTICAL
            layoutManager = lManager
            addItemDecoration(DividerItemDecoration(context, lManager.orientation))
        }

        viewModel.tasks.observe(this) {
            adapter.submitList(it)
        }
    }

}