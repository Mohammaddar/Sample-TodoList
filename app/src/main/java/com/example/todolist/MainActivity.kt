package com.example.todolist

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAGLC","onCreate")

        checkNightMode()

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        val adapter = TasksRecyclerAdapter {
        }
        binding.recyclerTasks.apply {
            this.adapter = adapter
            val lManager = LinearLayoutManager(this@MainActivity)
            lManager.orientation = RecyclerView.VERTICAL
            layoutManager = lManager
            addItemDecoration(DividerItemDecoration(context, lManager.orientation))
        }

        val ls = List(4) { "Task number $it" }
        adapter.submitList(ls)

        binding.btnNightMode.setOnClickListener {
            toggleNightMode()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAGLC","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAGLC","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TAGLC","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAGLC","onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAGLC","onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("TAGLC","onRestart")
    }

    private fun toggleNightMode() {
        val sharedPreferences = getSharedPreferences("SharedPref", MODE_PRIVATE)
        val nightMode = sharedPreferences.getBoolean("nightMode", false)
        sharedPreferences.edit()
            .putBoolean("nightMode", !nightMode)
            .apply()

        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    private fun checkNightMode(){
        val sharedPreferences = getSharedPreferences("SharedPref", MODE_PRIVATE)
        val nightMode = sharedPreferences.getBoolean("nightMode", false)
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}