package com.example.bmi

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)


        val results: ArrayList<Result> = arrayListOf(
            Result(150.0, 20.0,12.0, "data", "ok"),
            Result(150.0, 20.0,12.0, "data", "nie"),
            Result(150.0, 20.0,12.0, "data", "może"),
                    Result(150.0, 20.0,12.0, "data", "ok"),
        Result(150.0, 20.0,12.0, "data", "nie"),
        Result(150.0, 20.0,12.0, "data", "może"),
            Result(150.0, 20.0,12.0, "data", "ok"),
            Result(150.0, 20.0,12.0, "data", "nie"),
            Result(150.0, 20.0,12.0, "data", "może"),Result(150.0, 20.0,12.0, "data", "ok"),
            Result(150.0, 20.0,12.0, "data", "nie"),
            Result(150.0, 20.0,12.0, "data", "może")
        )

        my_recycler_view.layoutManager = LinearLayoutManager(this)
        my_recycler_view.adapter = ResultAdapter(results)
    }
}