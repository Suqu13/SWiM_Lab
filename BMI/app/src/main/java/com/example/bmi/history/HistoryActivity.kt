package com.example.bmi.history

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.example.bmi.MainActivity
import com.example.bmi.R
import com.example.bmi.services.HistoryService
import com.example.bmi.services.SharedPreferencesService
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        my_recycler_view.layoutManager = LinearLayoutManager(this)
        my_recycler_view.adapter = ResultAdapter(HistoryService.getResultsHistory())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId){
            R.id.clear -> {
                SharedPreferencesService.removeKeyValue(MainActivity.BMI_HISTORY_KEY)
                HistoryService.clearHistoryService()
                finish()
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

}