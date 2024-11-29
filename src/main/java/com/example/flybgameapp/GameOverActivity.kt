package com.example.flybgameapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.flybgameapp.MainActivity


class GameOverActivity : AppCompatActivity() {
    private var StartGameAgain: Button? = null
    private var DisplayScore: TextView? = null
    private var DisplayHighScore: TextView? = null
    private var score: String? = null
    private var highScore: String? = null

    //flyingFishView f = new flyingFishView(this);
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        // Retrieve score and high score from intent
        val extras = intent.extras
        if (extras != null) {
            score = extras.getString("score")
            highScore = extras.getString("highScore")
        }
        StartGameAgain = findViewById<View>(R.id.play_again_btn) as Button
        DisplayScore = findViewById<View>(R.id.displayScore) as TextView
        DisplayHighScore = findViewById<View>(R.id.displayHighScore) as TextView
        StartGameAgain!!.setOnClickListener {
            val mainIntent = Intent(
                this@GameOverActivity,
                MainActivity::class.java
            )
            startActivity(mainIntent)
        }
        DisplayScore!!.text = "Score :$score"
        DisplayHighScore!!.text = "High Score : $highScore"
    }
}