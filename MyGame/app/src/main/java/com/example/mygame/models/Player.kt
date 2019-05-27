package com.example.mygame.models

import android.content.Context
import android.graphics.*
import com.example.mygame.R


class Player(context: Context, x: Int, y: Int, private val screenSizeX : Int, private val screenSizeY : Int) {

    companion object {
        const val GRAVITY = 25
        private const val STAR_SCORE = 5
        private const val FIRST_JUMP_LENGTH = 400
        private const val SECOND_JUMP_LENGTH = 300
        private const val PLAYER_RECT_TRANSLATION = 100
    }

    var x = x
        private set
    var y = y
        private set
    var score = 0
        private set
    var isAlive = true
        private set
    var jumpIndicator = false
        private set

    val bitmap : Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.peppa)
    private var jumpPosition  = y
    private var currentSpeed = 0
    private var jumpCounter = 0
    private var attached  = false

    val playerRect = Rect(x, y + bitmap.height - PLAYER_RECT_TRANSLATION, x + bitmap.width, y + bitmap.height)
    val playerRectScore = Rect(x, y, x + bitmap.width, y + bitmap.height)


    fun jump() {
        if (jumpCounter < 1) {
            jumpPosition -= if (jumpCounter == 0) FIRST_JUMP_LENGTH else SECOND_JUMP_LENGTH
            jumpIndicator = true
            jumpCounter++
        }
    }

    fun updateX(newX : Int) {
        x -= newX
        if (x <= 0)
            x = 0
        else if (x + bitmap.width >= screenSizeX)
            x = screenSizeX - bitmap.width
       updateXRect()
    }

    private fun updateXRect() {
        playerRect.run {
            left = x
            right = x + bitmap.width
        }
        playerRectScore.run {
            left = x
            right = x + bitmap.width
        }
    }

    fun updateY(stairSpeed : Int) {
        currentSpeed = differentiateState(stairSpeed)
        y += currentSpeed
        if (y <= -bitmap.height) {
            y = -bitmap.height
            jumpPosition = y
        }
        findIfDead()
        updateJumpPosition()
        updateYRect()
    }

    private fun updateJumpPosition() {
        if (!jumpIndicator)
            jumpPosition = y
    }

    private fun findIfDead() {
        isAlive = y <= screenSizeY + bitmap.height
    }

    private fun differentiateState(stairSpeed: Int) : Int{
        return if (y > jumpPosition) {
            -GRAVITY
        } else {
            jumpIndicator = false
            differentiateBeingAttachedAndFalling(stairSpeed)
        }
    }

    private fun differentiateBeingAttachedAndFalling(stairSpeed: Int) : Int{
        return if (attached) {
            jumpCounter = 0
            attached = false
            stairSpeed
        } else {
            GRAVITY
        }
    }

    fun scoreCoin() {
        score ++
    }

    fun scoreStar() {
        score += STAR_SCORE
    }

    private fun updateYRect() {
        playerRect.run {
            top = y + bitmap.height - PLAYER_RECT_TRANSLATION
            bottom = y + bitmap.height
        }
        playerRectScore.run {
            top = y
            bottom = y + bitmap.height
        }
    }

    fun attach() {
        if (!jumpIndicator)
            attached = true
    }

}