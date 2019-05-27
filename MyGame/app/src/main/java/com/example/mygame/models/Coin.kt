package com.example.mygame.models

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.example.mygame.R
import java.util.*

class Coin(context: Context, stairBeginning : Int, stairWidth : Int, currentStairPositionY: Int) : InteractionScoreObject {

    companion object {
        private const val COIN_TRANSLATION = 100
    }

    val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.coin)!!
    var x = stairBeginning + Random().nextInt(stairWidth)
    var y = currentStairPositionY - COIN_TRANSLATION
        private set
    val coinRec = Rect(x, y, x + bitmap.width, y + bitmap.height)


    override fun updateY(speed : Int) {
        y += speed
        coinRec.top  = y
        coinRec.bottom = y + bitmap.height
    }

    override fun setNewXAndY(stairBeginning : Int, stairWidth : Int, currentStairPositionY: Int) {
        y = currentStairPositionY - COIN_TRANSLATION
        x = stairBeginning + Random().nextInt(stairWidth)
        coinRec.left  = x
        coinRec.right = x + bitmap.width
    }

}
