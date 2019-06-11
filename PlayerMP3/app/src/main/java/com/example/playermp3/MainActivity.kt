package com.example.playermp3

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.RecyclerView
import com.example.playermp3.recycleView.AudioAdapter
import com.example.playermp3.repo.AudioRepository


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: AudioAdapter
    private lateinit var manager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        AudioRepository.loadAudioFiles(contentResolver)
        val myFragment = launchFragment()
        manager = LinearLayoutManager(this)
        adapter = AudioAdapter(this, myFragment)
        songs_recycler_view.layoutManager = manager
        songs_recycler_view.adapter = adapter
        songs_recycler_view.setHasFixedSize(true)
        songs_recycler_view.setItemViewCacheSize(50)
    }

    private fun launchFragment() : PlayerFragment{
        val playerFragment = PlayerFragment.newInstance()
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().run {
            add(R.id.fragment_container, playerFragment)
            show(playerFragment)
            commit()
        }
        return playerFragment
    }
}