package com.example.playermp3

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.getDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import com.example.playermp3.repo.AudioRepository
import com.example.playermp3.services.AudioService
import kotlinx.android.synthetic.main.fragment_player.*
import kotlinx.android.synthetic.main.fragment_player.view.*
import android.support.v4.app.ActivityOptionsCompat



class PlayerFragment : Fragment(), PlayerInterface {

    private val handler = Handler()
    private lateinit var runnable: Runnable
    private lateinit var progressBar: ProgressBar
    private lateinit var button: ImageButton
    private var isActive = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_player, container, false)
        progressBar = view.findViewById(R.id.progress_bar_fragment)
        button = view.findViewById(R.id.play_pause_fragment_btn)

        button.setOnClickListener {
            if (AudioRepository.isInitialized()) {
                reactOnPPButtonPressed(context!!, button)
            }
        }
        view.setOnClickListener {
            if (AudioRepository.isInitialized())
                launchActivity()
        }
        return view
    }

    fun initPlayingFromAdapter() {
        play_pause_fragment_btn.setImageDrawable(getDrawable(context!!, R.drawable.pause))
        AudioService.onPlayerActions(this, false)
        AudioService.mediaPlayer.setOnCompletionListener {
            AudioService.onPlayerActions(this)
        }
    }

    private fun launchActivity() {
        val intent = Intent(context, PlayerActivity::class.java)
        val bundle = ActivityOptionsCompat.makeCustomAnimation(
            context!!,
            android.R.anim.fade_in, android.R.anim.fade_out
        ).toBundle()
        ContextCompat.startActivity(context!!, intent, bundle)
    }

    override fun loadView() {
        if (AudioRepository.currentAudio != -1) {
            view!!.title_fragment.text = AudioRepository.getCurrent().title
            view!!.artist_fragment.text = AudioRepository.getCurrent().artist
            progressBar.max = AudioRepository.getCurrent().duration
        }
    }

    override fun showProgress() {
        if (AudioService.mediaPlayer.isPlaying) {
            progressBar.progress = AudioService.mediaPlayer.currentPosition
            runnable = Runnable {
                if (isActive)
                    activity!!.runOnUiThread {
                        showProgress()
                    }
            }
            handler.postDelayed(runnable, 1000)
        }
    }

    override fun onResume() {
        super.onResume()
        isActive = true
        loadView()
        showProgress()
        initPPButton(context!!, button)
        if (AudioService.mediaPlayer.isPlaying)
            AudioService.mediaPlayer.setOnCompletionListener {
                AudioService.onPlayerActions(this)
            }
    }

    override fun onPause() {
        super.onPause()
        isActive = false
    }

    companion object {
        @JvmStatic
        fun newInstance(): PlayerFragment {
            return PlayerFragment()
        }
    }
}