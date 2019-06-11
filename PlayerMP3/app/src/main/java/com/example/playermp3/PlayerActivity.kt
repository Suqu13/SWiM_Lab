package com.example.playermp3

import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import com.example.playermp3.repo.AudioRepository
import com.example.playermp3.services.TimeConverterService.convertTime
import com.example.playermp3.services.AudioService
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity: AppCompatActivity(), PlayerInterface{

    private val handler = Handler()
    private lateinit var runnable: Runnable
    private var autoChange = true

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        loadView()
        showProgress()
        initPPButton(this, play_pause_activity_btn)
        next.setOnClickListener{
            autoChange = false
            AudioRepository.getNext()
            AudioService.onPlayerActions(this, autoChange)

        }
        previous.setOnClickListener{
                autoChange = false
                AudioRepository.getPrevious()
                AudioService.onPlayerActions(this, autoChange)
        }
        play_pause_activity_btn.setOnClickListener{
            reactOnPPButtonPressed(this, play_pause_activity_btn)
        }
    }

    override fun loadView() {
        manager_title.text = AudioRepository.getCurrent().title
        manager_artist.text = AudioRepository.getCurrent().artist
        manager_album.text = AudioRepository.getCurrent().album
        loadCover()
        initSeekBarAndCounters()
        setSeekBarChangedListener()
    }

    private fun initSeekBarAndCounters() {
        whole_time.text = convertTime(AudioRepository.getCurrent().duration)
        seek_bar.max = AudioRepository.getCurrent().duration
    }

    private fun loadCover() {
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(AudioRepository.getCurrent().path)
        val cover = mediaMetadataRetriever.embeddedPicture
        if (cover != null) {
            val coverImage = BitmapFactory.decodeByteArray(cover, 0, cover.size)
            manager_cover.setImageBitmap(coverImage)
        } else {
            manager_cover.setImageResource(R.drawable.music)
        }
    }

    private fun setSeekBarChangedListener() {
        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    AudioService.mediaPlayer.seekTo(progress)
                    showProgress()
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    override fun showProgress() {
        if (AudioService.mediaPlayer.isPlaying) {
            seek_bar.progress = AudioService.mediaPlayer.currentPosition
            current_time.text = convertTime(AudioService.mediaPlayer.currentPosition)
            runnable = Runnable {
                runOnUiThread {
                    showProgress()
                }
            }
            handler.postDelayed(runnable, 1000)
        }
    }

    override fun onResume() {
        super.onResume()
        AudioService.mediaPlayer.setOnCompletionListener {
            AudioService.onPlayerActions(this, autoChange)
            autoChange = true
        }
    }
}