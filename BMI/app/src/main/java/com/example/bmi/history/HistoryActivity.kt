package com.example.bmi

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.bmi.services.HistoryService
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        my_recycler_view.layoutManager = LinearLayoutManager(this)
        my_recycler_view.adapter = ResultAdapter(HistoryService.getResultsHistory())
    }
}