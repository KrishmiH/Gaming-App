package com.example.flybgameapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.flybgameapp.MainActivity

class SplashActivity : AppCompatActivity() {

    private var StartGame: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        StartGame = findViewById<View>(R.id.play_btn) as Button
        StartGame!!.setOnClickListener(View.OnClickListener {
            val mainIntent = Intent(
                this@SplashActivity,
                MainActivity::class.java
            )
            startActivity(mainIntent)
        })
    }


    override fun onPause() {
        super.onPause()
        finish()
    }


}