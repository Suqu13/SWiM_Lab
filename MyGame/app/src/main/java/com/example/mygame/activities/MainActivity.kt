package com.example.mygame.activities


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.mygame.R
import kotlinx.android.synthetic.main.activity_fullscreen.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_fullscreen)
        play_button.setOnClickListener {startActivity(Intent(this, GameActivity::class.java))}
    }
}
