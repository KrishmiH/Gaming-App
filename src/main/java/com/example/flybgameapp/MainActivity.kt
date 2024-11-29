package com.example.flybgameapp

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.flybgameapp.FlyingBeeView
import com.example.flybgameapp.R
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {

    private var gameView: FlyingBeeView? = null
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameView = FlyingBeeView(this)
        setContentView(gameView)

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Runnable { gameView!!.invalidate() })
            }
        }, 0, MainActivity.Interval)


    }

    companion object {
        val Interval: Long = 30
    }
}