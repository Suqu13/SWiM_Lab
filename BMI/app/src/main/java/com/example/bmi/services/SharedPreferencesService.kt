package com.example.bmi

import android.app.Activity
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPreferencesService {
    private var sharedPref: SharedPreferences? = null

    fun createSharedPref(act: Activity) {
        if (sharedPref == null)
            sharedPref = act.getPreferences(0)
    }

    fun loadData(): ArrayList<Result> {
        val resultsJson = sharedPref!!.getString("results", "[]")
        class Token : TypeToken<ArrayList<Result>>()
        return Gson().fromJson(resultsJson, Token().type)
    }

    fun commitChanges(results: ArrayList<Result>) {
        val editor = sharedPref!!.edit()
        val resultsJson = Gson().toJson(results)
        editor.putString("results", resultsJson).apply()
    }

    fun clearData() {
        sharedPref!!.edit().clear().apply()
    }
}