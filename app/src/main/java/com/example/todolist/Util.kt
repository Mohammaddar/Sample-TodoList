package com.example.todolist

import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

enum class TaskTabLayoutState {
    All,
    Active,
    Completed
}

fun toggleNightMode(context: Context) {
    val sharedPreferences =
        context.getSharedPreferences("SharedPref", AppCompatActivity.MODE_PRIVATE)
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

fun checkNightMode(context: Context) {
    val sharedPreferences =
        context.getSharedPreferences("SharedPref", AppCompatActivity.MODE_PRIVATE)
    val nightMode = sharedPreferences.getBoolean("nightMode", false)
    if (nightMode) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}