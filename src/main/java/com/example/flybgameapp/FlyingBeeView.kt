package com.example.flybgameapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast

class FlyingBeeView (context: Context) : View(context){
    private val bee = arrayOfNulls<Bitmap>(2)
    private val beeX = 10
    private var beeY: Int
    private var beeSpeed = 0
    private var canvasWidth = 0
    private var canvasHeight = 0

    //yellow Ball
    private var yellowX = 0
    private var yellowY = 0
    private val yellowSpeed = 10
    private val yellowPaint = Paint()

    //green Ball
    private var greenX = 0
    private var greenY = 0
    private val greenSpeed = 15
    private val greenPaint = Paint()

    //red Ball
    private var redX = 0
    private var redY = 0
    private val redSpeed = 20
    private val redPaint = Paint()

    var score: Int
    var highScore: Int
    var lifeCounterofBee: Int
    private val sharedPreferences: SharedPreferences
    private var touch = false
    private val backgroundImage: Bitmap
    private val scorePaint = Paint()
    private val life = arrayOfNulls<Bitmap>(2)

    init {
        bee[0] = BitmapFactory.decodeResource(resources, R.drawable.bee1)
        bee[1] = BitmapFactory.decodeResource(resources, R.drawable.bee2)
        backgroundImage = BitmapFactory.decodeResource(resources, R.drawable.background)

        //yellow
        yellowPaint.color = Color.YELLOW
        yellowPaint.isAntiAlias = false

        //green
        greenPaint.color = Color.GREEN
        greenPaint.isAntiAlias = false

        //red
        redPaint.color = Color.RED
        redPaint.isAntiAlias = false

        scorePaint.color = Color.WHITE
        scorePaint.textSize = 70f
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD)
        scorePaint.isAntiAlias = true
        life[0] = BitmapFactory.decodeResource(resources, R.drawable.hearts)
        life[1] = BitmapFactory.decodeResource(resources, R.drawable.heart_grey)
        beeY = 550
        score = 0
        lifeCounterofBee = 3
        sharedPreferences = context.getSharedPreferences("GamePrefs", Context.MODE_PRIVATE)
        highScore = sharedPreferences.getInt("highScore", 0)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvasWidth = canvas.width
        canvasHeight = canvas.height

        canvas.drawBitmap(backgroundImage, 0f, 0f, null)
        val minFishY = bee[0]!!.height
        val maxFishY = canvasHeight - bee[0]!!.height * 3
        beeY = beeY + beeSpeed
        if (beeY < minFishY) {
            beeY = minFishY
        }
        if (beeY > maxFishY) {
            beeY = maxFishY
        }
        beeSpeed = beeSpeed + 2
        if (touch) {
            canvas.drawBitmap(bee[1]!!, beeX.toFloat(), beeY.toFloat(), null)
            touch = false
        } else {
            canvas.drawBitmap(bee[0]!!, beeX.toFloat(), beeY.toFloat(), null)
        }

        //yellow ball function
        yellowX = yellowX - yellowSpeed
        if (hitBallChecker(yellowX, yellowY)) {
            score = score + 10
            yellowX = -100
        }
        if (yellowX < 0) {
            yellowX = canvasWidth + 21
            yellowY = Math.floor(Math.random() * (maxFishY - minFishY)).toInt() + minFishY
        }
        canvas.drawCircle(yellowX.toFloat(), yellowY.toFloat(), 35f, yellowPaint)

        //green ball function
        greenX = greenX - greenSpeed
        if (hitBallChecker(greenX, greenY)) {
            score = score + 20
            greenX = -100
        }
        if (greenX < 0) {
            greenX = canvasWidth + 21
            greenY = Math.floor(Math.random() * (maxFishY - minFishY)).toInt() + minFishY
        }
        canvas.drawCircle(greenX.toFloat(), greenY.toFloat(), 35f, greenPaint)

        //red ball function
        redX = redX - redSpeed
        if (hitBallChecker(redX, redY)) {
            redX = -100
            lifeCounterofBee--
            if (lifeCounterofBee == 0) {
                Toast.makeText(context, "Game Over", Toast.LENGTH_SHORT).show()
                val gameOverIntent = Intent(context, GameOverActivity::class.java)
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                val extras = Bundle()
                extras.putString("score", score.toString())
                extras.putString("highScore", highScore.toString())
                gameOverIntent.putExtras(extras)
                context.startActivity(gameOverIntent)
            }
        }
        if (redX < 0) {
            redX = canvasWidth + 21
            redY = Math.floor(Math.random() * (maxFishY - minFishY)).toInt() + minFishY
        }
        canvas.drawCircle(redX.toFloat(), redY.toFloat(), 45f, redPaint)

        //score funnction
        canvas.drawText("Score :$score", 20f, 70f, scorePaint)

        //red ball life decreasing function
        for (i in 0..2) {
            val x = (580 + life[0]!!.width * 1.5 * i).toInt()
            val y = 30
            if (i < lifeCounterofBee) {
                canvas.drawBitmap(life[0]!!, x.toFloat(), y.toFloat(), null)
            } else {
                canvas.drawBitmap(life[1]!!, x.toFloat(), y.toFloat(), null)
            }
        }

        //high score
        canvas.drawText("High Score: $highScore", 20f, 140f, scorePaint)
    }

    //update highscore
    fun updateHighScore(score: Int) {
        if (score > highScore) {
            highScore = score
            val editor = sharedPreferences.edit()
            editor.putInt("highScore", highScore)
            editor.apply()
        }
    }

    //Update the hitBallChecker method to call updateHighScore
    fun hitBallChecker(x: Int, y: Int): Boolean {
        if (beeX < x && x < beeX + bee[0]!!
                .width && beeY < y && y < beeY + bee[0]!!.height
        ) {
            updateHighScore(score)
            return true
        }
        return false
    }

    //Intent gameOverIntent = new Intent(getContext(), GameOverActivity.class);
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            touch = true
            beeSpeed = -22
        }
        return true
    }
}