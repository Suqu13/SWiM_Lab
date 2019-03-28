package com.example.bmi.services

import com.example.bmi.history.Result
import java.text.SimpleDateFormat
import java.util.*

object HistoryService {
    private  var resultsHistory: ArrayList<Result> = SharedPreferencesService.loadData()
    private var maxSize: Int = 10

    private fun addHistoryRecord(result: Result) {
        if(resultsHistory.size == maxSize)
            resultsHistory.removeAt(maxSize - 1)
        resultsHistory.add(0, result)

    }

    fun getResultsHistory() = resultsHistory

    fun isHistoryEmpty() = resultsHistory.isEmpty()

    fun prepareHistoryRecord(result: Double, height: String, weight: String, status: String, units: Boolean) {
        val (weightU, heightU) = if (units) {
            Pair("[lbs]","[inches]")
        } else {
            Pair("[kg]","[cm]")
        }
        HistoryService.addHistoryRecord(
            Result(
                "$height $heightU", "$weight $weightU", "%.2f".format(result),
                SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date()), status
            )
        )
    }
}
