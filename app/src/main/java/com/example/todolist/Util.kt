package com.example.todolist

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.todolist.ui.screenshotActivity.ScreenShotActivity

enum class TaskTabLayoutState {
    All,
    Active,
    Completed
}

fun animateNightModeToggle(
    application: Application,
    activity: AppCompatActivity,
    cx: Float,
    cy: Float
) {
    val bitmap = takeScreenShot(activity)
    if (bitmap != null) {
        App.screen = bitmap
    }
    val intent = Intent(activity, ScreenShotActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
    intent.putExtra(
        "cx", cx
    )
    intent.putExtra(
        "cy", cy
    )
    activity.startActivity(intent)
    nightModeToggle(application)
}

private fun nightModeToggle(context: Context) {
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

fun takeScreenShot(activity: AppCompatActivity): Bitmap? {
    val v1 = activity.window.decorView.rootView
    v1.isDrawingCacheEnabled = true
    val bitmap = Bitmap.createBitmap(v1.drawingCache)
    v1.isDrawingCacheEnabled = false
    return bitmap
}