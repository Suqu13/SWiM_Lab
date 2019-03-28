package com.example.bmi.services

import android.app.Activity
import android.content.SharedPreferences
import com.example.bmi.MainActivity
import com.example.bmi.history.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPreferencesService {
    private var sharedPref: SharedPreferences? = null

    fun createSharedPref(act: Activity) {
        if (sharedPref == null)
            sharedPref = act.getSharedPreferences("bmi_history_file",0)
    }

    fun loadHistoryData(): ArrayList<Result> {
        val resultsJson = sharedPref!!.getString(MainActivity.BMI_HISTORY_KEY, "[]")
        class Token : TypeToken<ArrayList<Result>>()
        return Gson().fromJson(resultsJson, Token().type)
    }

    fun commitHistoryChanges(results: ArrayList<Result>) {
        val editor = sharedPref!!.edit()
        val resultsJson = Gson().toJson(results)
        editor.putString(MainActivity.BMI_HISTORY_KEY, resultsJson).apply()
    }

    fun removeKeyValue(key: String) {
        sharedPref!!.edit().remove(key).apply()
    }

    fun clearData() {
        sharedPref!!.edit().clear().apply()
    }
}