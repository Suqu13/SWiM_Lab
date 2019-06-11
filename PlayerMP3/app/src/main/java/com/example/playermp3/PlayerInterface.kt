package com.example.playermp3

import android.content.Context
import android.support.v4.content.ContextCompat.getDrawable
import android.widget.ImageButton
import com.example.playermp3.services.AudioService

interface PlayerInterface {

    fun loadView()

    fun showProgress()

    fun initPPButton(context : Context, button: ImageButton){
        if (AudioService.mediaPlayer.isPlaying) {
            button.setImageDrawable(getDrawable(context ,R.drawable.pause))
        } else {
            button.setImageDrawable(getDrawable(context, R.drawable.play))
        }
    }

    fun reactOnPPButtonPressed(context : Context, button: ImageButton) {
        AudioService.playStopAudio()
        initPPButton(context, button)
    }
}