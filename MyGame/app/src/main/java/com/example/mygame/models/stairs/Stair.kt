package com.example.mygame.models.stairs

import android.content.Context
import android.graphics.Rect
import com.example.mygame.models.Coin
import com.example.mygame.models.Player
import com.example.mygame.models.Star

open class Stair(context: Context, protected val screenSizeX : Int, protected val screenSizeY : Int, y: Int, protected val lastY : Int) {

    companion object {
        const val STAIR_ENDING_TRANSLATION = 300
        const val STAIR_HEIGHT = 70
        const val STAIR_BASIC_SPEED = 7
    }

    var y = y
        protected set
    var x = 0
        protected set
    var levelFactor = 1
        protected set
    var width = screenSizeX
        protected set

    open val stairRec: Rect = Rect(x, y, x + width, y)
    open val coin = Coin(context, x, width, y)
    open val star = Star(context, x, width, y)

    open fun update(jumpIndicator: Boolean) {
        val speed = if (jumpIndicator) Player.GRAVITY else STAIR_BASIC_SPEED
        y += speed
        if (y >= screenSizeY) {
            setAfterProjection()
        } else {
            setDuringProjection(speed)
        }
    }

    private fun setAfterProjection() {
        y = lastY + STAIR_ENDING_TRANSLATION
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