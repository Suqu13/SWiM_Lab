package com.example.mygame.views

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.view.SurfaceView
import com.example.mygame.R
import com.example.mygame.models.Cloud
import com.example.mygame.models.Coin
import com.example.mygame.models.Player
import com.example.mygame.models.Star
import com.example.mygame.models.stairs.*
import java.util.*
import kotlin.collections.ArrayList

class GameView(context: Context, private  val screenSizeX : Int, private val screenSizeY : Int) : SurfaceView(context), Runnable {

    companion object {
        private const val SLEEP_TIME = 10L
        private const val LIGHT_CLASSIFIER = 100
        private const val CLOUD_GAP = 600
        private const val BASIC_STAIR_GAP = 300
        private const val STAIR_GAP_FACTOR = 900
        private const val MOVE_TO_HIDE = -300
        private const val STAIRS_NUMBER = 30
        private const val CLOUDS_NUMBER = 5
        private val COIN_COORDINATES = Pair(10f, 10f)
        private val SCORE_TEXT_COORDINATES = Pair(80f, 65f)
        private const val SCORE_TEXT_SIZE = 60f
        private const val GAME_OVER_TEXT = "Przegrałeś!"
        private const val GAME_OVER_TEXT_SIZE = 150f
        private const val GAME_OVER_SCORE_TEXT = "Wynik: "
        private const val GAME_OVER_SCORE_TEXT_SIZE = 75f
    }

    private var playing: Boolean = false
    private var gameThread: Thread? = null
    private val startPointYStair : Int = (screenSizeY*0.95).toInt()
    private val startPointXPlayer = (screenSizeX*0.5).toInt()
    private val startPointYPlayer = (screenSizeY*0.95).toInt() - 290
    private val player : Player
    private val stairs = ArrayList<Stair>()
    private val clouds = ArrayList<Cloud>()
    private var isStarted = false
    private var showStar = false
    private val paint = Paint()

    private lateinit var canvas : Canvas


    init {
        player = Player(
            context,
            startPointXPlayer,
            startPointYPlayer,
            screenSizeX,
            screenSizeY
        )
        initStairs(screenSizeX, screenSizeY, STAIRS_NUMBER)
        initClouds(screenSizeX, screenSizeY, CLOUDS_NUMBER)
    }

    private fun initStairs(screenSizeX: Int, screenSizeY: Int, numOfStairs : Int) {
        var actDistanceFromStart = 0
        val distances = Array(numOfStairs) {i -> if (i == 0) 0 else -Random().nextInt(STAIR_GAP_FACTOR) - BASIC_STAIR_GAP }
        val sumOfDistances = distances.sum()
        for (i in 0 until numOfStairs) {
            actDistanceFromStart += distances[i]
            stairs.add(
                if (i == 0)
                    Stair(context,
                        screenSizeX,
                        screenSizeY,
                        startPointYStair ,
                        sumOfDistances + screenSizeY
                    )
                else
                    NormalStair(context,
                        screenSizeX,
                        screenSizeY,
                        startPointYStair + actDistanceFromStart,
                        sumOfDistances + screenSizeY
                    )
            )
        }
    }

    private fun initClouds(screenSizeX: Int, screenSizeY: Int, numOfClouds: Int) {
        for (i in 0 until numOfClouds) {
            clouds.add(Cloud(context, screenSizeX, screenSizeY, i * CLOUD_GAP))
        }
    }

    override fun run() {
        while (playing && player.isAlive) {
            update()
            draw()
            control()
        }
    }

     fun jump() {
        player.jump()
    }

    private fun update() {
        if (isStarted) {
            clouds.map{ it.update() }
            player.updateY(Stair.STAIR_BASIC_SPEED)
        }
        for (stair in stairs) {
            if (isStarted)
                stair.update(player.jumpIndicator)
            if (showStar) {
                if (Rect.intersects(player.playerRectScore, stair.star.starRec)) {
                    hideStar(stair.star)
                    player.scoreStar()
                }
            }
            if (Rect.intersects(player.playerRect, stair.stairRec))
                player.attach()
            if (Rect.intersects(player.playerRectScore, stair.coin.coinRec)) {
                hideCoin(stair.coin)
                player.scoreCoin()
            }
        }
    }

    private fun hideCoin(coin: Coin) {
        coin.run {
            x = MOVE_TO_HIDE
            coinRec.left = MOVE_TO_HIDE
            coinRec.right = MOVE_TO_HIDE
        }
    }

   private fun hideStar(star: Star) {
       star.run {
           x = MOVE_TO_HIDE
           starRec.left = MOVE_TO_HIDE
           starRec.right = MOVE_TO_HIDE
       }
   }

    private fun draw() {
        if (holder.surface.isValid) {
            val backgroundColor = if (!showStar) R.color.colorSky else R.color.night_sky
            val stairsColor = if (!showStar) Color.WHITE else Color.DKGRAY
            canvas = holder.lockCanvas()
            canvas.drawColor(ContextCompat.getColor(context, backgroundColor))
            drawScoreTable()
            for (stair in stairs) {
               drawInteractiveElements(stair, stairsColor)
            }
            for (cloud in clouds) {
                drawCloud(cloud)
            }
            drawPlayer()
            drawGameOver()
            holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun drawGameOver() {
        if (!player.isAlive) {
            drawTextMiddleX(screenSizeY/2f, GAME_OVER_TEXT_SIZE, Color.BLACK, GAME_OVER_TEXT)
            drawTextMiddleX(screenSizeY/2f + 150f, GAME_OVER_SCORE_TEXT_SIZE, Color.BLACK, GAME_OVER_SCORE_TEXT + player.score)
        }
    }

    private fun drawInteractiveElements(stair : Stair, stairColor: Int) {
        drawStair(stair, stairColor)
        drawCoin(stair.coin)
        drawStarIfPossible(stair.star)
    }

    private fun drawScoreTable() {
        drawCoin(COIN_COORDINATES.first, COIN_COORDINATES.second)
        drawText(SCORE_TEXT_COORDINATES.first, SCORE_TEXT_COORDINATES.second, SCORE_TEXT_SIZE, Color.BLACK, "x ${player.score}")
    }

    private fun drawStarIfPossible(star: Star) {
        if (showStar)
            drawStar(star)
    }

    private fun drawTextMiddleX(y: Float, myTextSize: Float, myTextColor: Int, myText: String) {
        paint.run {
            textSize = myTextSize
            color = myTextColor
            textAlign = Paint.Align.CENTER
        }
        val x = (canvas.width / 2) - ((paint.descent() + paint.ascent()) / 2)
        canvas.drawText(myText, x, y, paint)
    }

    private fun drawText(x: Float, y: Float, textSize: Float, textColor: Int, text: String) {
        paint.textSize = textSize
        paint.color = textColor
        canvas.drawText(text, x, y, paint)
    }

    private fun drawStair(stair: Stair, stairColor: Int)  {
        paint.color = stairColor
        canvas.drawRect(
            stair.x.toFloat(),
            stair.y.toFloat(),
            (stair.x + stair.width).toFloat(),
            (stair.y + Stair.STAIR_HEIGHT).toFloat(),
            paint
        )
    }

    private fun drawStar(star: Star) {
        canvas.drawBitmap(star.bitmap, star.x.toFloat(), star.y.toFloat(), null)
    }

    private fun drawCoin(coin: Coin) {
        canvas.drawBitmap(coin.bitmap, coin.x.toFloat(), coin.y.toFloat(), null)
    }

    private fun drawCoin(x: Float, y: Float) {
        canvas.drawBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.coin), x, y, null)
    }

    private fun drawCloud(cloud: Cloud) {
        canvas.drawBitmap(cloud.bitmap, cloud.x.toFloat(), cloud.y.toFloat(), null)
    }

    private fun drawPlayer() {
        canvas.drawBitmap(player.bitmap, player.x.toFloat(), player.y.toFloat(), null)
    }

    private fun control() {
        try {
            Thread.sleep(SLEEP_TIME)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun updatePlayerX(newX : Int) {
        player.updateX(newX)
    }

    fun pause() {
        playing = false
        try {
            gameThread!!.join()
        } catch (e: InterruptedException) {
        }
    }

    fun resume() {
        playing = true
        gameThread = Thread(this)
        gameThread!!.start()
    }

    fun startGame() {
        isStarted = true
    }

    fun showStar(lightValue : Float) {
        try {
            showStar = lightValue < LIGHT_CLASSIFIER
        } catch (exc: ConcurrentModificationException) {
            exc.printStackTrace()
        }
    }
}