package com.example.todolist

import android.app.Application
import android.graphics.Bitmap

class App : Application() {
    companion object {
        lateinit var screen: Bitmap
    }
}