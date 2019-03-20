package com.example.bmi

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class InfoActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val bundle = intent.extras
        findViewById<TextView>(R.id.resultInfo).text = bundle.getString("result")
        findViewById<TextView>(R.id.statusInfo).text = bundle.getString("status")
        findViewById<TextView>(R.id.descriptionInfo).text = bundle.getString("description")
    }
}