package com.example.mygame.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.mygame.R
import java.util.*

class Cloud(context : Context, private val screenSizeX : Int , private val screenSizeY : Int, y: Int) {

    var y = y
        private set
    var x : Int
        private set

    val bitmap: Bitmap
    private var idBmp : Int


    init {
        idBmp = when(Random().nextInt(3)) {
            0 -> R.drawable.cloud_a
            1 -> R.drawable.cloud_b
            else -> R.drawable.cloud_c
        }
        bitmap = BitmapFactory.decodeResource(context.resources, idBmp)!!
        x = Random().nextInt(screenSizeX) - bitmap.width/2
    }

    fun update() {
        y += 3
        if (y >= screenSizeY + bitmap.height) {
            y = -bitmap.height
            x  = Random().nextInt(screenSizeX)
            idBmp = when(Random().nextInt(3)) {
                0 -> R.drawable.cloud_a
                1 -> R.drawable.cloud_b
                else -> R.drawable.cloud_c
            }
        }
    }
}