package com.example.mygame.models.stairs

import android.content.Context
import android.graphics.Rect
import com.example.mygame.models.Coin
import com.example.mygame.models.Player
import com.example.mygame.models.Star
import java.util.*

class NormalStair(context: Context, screenSizeX : Int,  screenSizeY : Int, y: Int, lastY : Int):
    Stair(context, screenSizeX, screenSizeY, y, lastY) {

    companion object {
        private const val BASIC_NORMAL_STAIR_WIDTH = 100
        private const val NORMAL_STAIR_WIDTH_FACTOR = 400
    }

    override val stairRec: Rect = Rect()
    override val coin : Coin
    override val star : Star

    init {
        initializeNewBorders()
        coin = Coin(context, x, width, y)
        star = Star(context, x, width, y)
    }

    private fun initializeNewBorders() {
        findXAndWidth()
        updateStairRec()
    }

    private fun findXAndWidth() {
        x = Random().nextInt(screenSizeX)
        width = BASIC_NORMAL_STAIR_WIDTH + Random().nextInt((NORMAL_STAIR_WIDTH_FACTOR/levelFactor++))
        if (x + width > screenSizeX) {
            x -= screenSizeX/2
        }
    }

    private fun updateStairRec() {
        stairRec.run {
            left = x
            right = x + width
            top = y
            bottom = y
        }
    }

    override fun update(jumpIndicator : Boolean) {
        val speed= if (jumpIndicator) Player.GRAVITY else STAIR_BASIC_SPEED
        y += speed
        if (y >= screenSizeY) {
            setAfterProjection()
        } else {
            setDuringProjection(speed)
        }
    }

    private fun setAfterProjection() {
        y = lastY + STAIR_ENDING_TRANSLATION
        initializeNewBorders()
        coin.setNewXAndY(x, width, y)
        star.setNewXAndY(x, width, y)
    }

    private fun setDuringProjection(speed : Int) {
        coin.updateY(speed)
        star.updateY(speed)
        stairRec.run {
            top = y
            bottom = y
        }
    }

}