package com.example.playermp3.services

import android.media.MediaPlayer
import com.example.playermp3.model.AudioFile
import com.example.playermp3.repo.AudioRepository
import com.example.playermp3.PlayerInterface

object AudioService {

    val mediaPlayer = MediaPlayer()

    private fun resetMediaPlayer(audioFile: AudioFile) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(audioFile.path)
    }

    fun onPlayerActions(player: PlayerInterface, autoChange : Boolean = true) {
        if (autoChange) {
            AudioRepository.getNext()
        }
        resetMediaPlayer(AudioRepository.getCurrent())
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()
            player.showProgress()
            player.loadView()
        }
        mediaPlayer.prepareAsync()
    }

    fun playStopAudio() {
        if (!mediaPlayer.isPlaying)
            mediaPlayer.start()
        else
            mediaPlayer.pause()
    }
}