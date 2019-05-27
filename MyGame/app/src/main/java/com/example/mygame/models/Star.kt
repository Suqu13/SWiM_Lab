package com.example.mygame.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.example.mygame.R
import java.util.*

class Star(context: Context, stairBeginning : Int, stairWidth : Int, currentStairPositionY: Int) : InteractionScoreObject {

    companion object {
        private const val MOVE_TO_HIDE_STAR = -300
        private const val STAR_TRANSLATION = 100
        private const val STAR_FACTOR = 4
    }

    val bitmap : Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.star)!!
    var x = if (Random().nextInt(STAR_FACTOR) == 0) stairBeginning + Random().nextInt(stairWidth) else MOVE_TO_HIDE_STAR
    var y = currentStairPositionY - STAR_TRANSLATION
        private set
    val starRec = Rect(x, y, x + bitmap.width, y + bitmap.height)


    override fun updateY(speed : Int) {
        y += speed
        starRec.top  = y
        starRec.bottom = y + bitmap.height
    }

    override fun setNewXAndY(stairBeginning : Int, stairWidth : Int, currentStairPositionY: Int) {
        y = currentStairPositionY - STAR_TRANSLATION
        x = if (Random().nextInt(STAR_FACTOR) == 0) stairBeginning + Random().nextInt(stairWidth) else MOVE_TO_HIDE_STAR
        starRec.left  = x
        starRec.right = x + bitmap.width
    }

}
