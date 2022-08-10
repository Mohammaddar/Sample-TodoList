package com.example.todolist.ui.screenshotActivity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.util.Log
import android.view.ViewAnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.App
import com.example.todolist.R
import kotlin.math.hypot


class ScreenShotActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screenshot)
        Log.d("TAGLC","onCreate")
    }


    override fun onResume() {
        super.onResume()

        Log.d("TAGLC","onResume")
        val screen = App.screen
        val imgScreen = findViewById<ImageView>(R.id.img_screen)
        imgScreen.setImageBitmap(screen)

        val cx = intent.extras?.getFloat("cx") ?: 110
        val cy = intent.extras?.getFloat("cy") ?: 110
        imgScreen.post {
            val initialRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()
            val anim =
                ViewAnimationUtils.createCircularReveal(
                    imgScreen,
                    cx.toInt(),
                    cy.toInt(),
                    initialRadius,
                    0f
                )
            anim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
//                    imgScreen.visibility = View.INVISIBLE
                    finish()
                }
            })
            anim.start()
        }
    }
}