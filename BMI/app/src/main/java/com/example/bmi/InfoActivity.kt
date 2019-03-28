package com.example.bmi

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val bundle = intent.extras
        bmi_value_textView_info.text = bundle!!.getString(MainActivity.RESULT_KEY)
        status_textView_info.text = bundle.getString(MainActivity.STATUS_KEY)
        description_textView_info.text = bundle.getString(MainActivity.DESCRIPTION_KEY)
    }
}