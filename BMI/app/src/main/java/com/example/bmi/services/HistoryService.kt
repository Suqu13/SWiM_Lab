package com.example.bmi.services

import com.example.bmi.history.Result
import java.text.SimpleDateFormat
import java.util.*

object HistoryService {
    private  var resultsHistory: ArrayList<Result> = SharedPreferencesService.loadHistoryData()
    private var maxSize: Int = 10

    private fun addHistoryRecord(result: Result) {
        if(resultsHistory.size == maxSize)
            resultsHistory.removeAt(maxSize - 1)
        resultsHistory.add(0, result)
    }

    fun getResultsHistory() = resultsHistory

    fun clearHistoryService() = resultsHistory.clear()

    fun isHistoryEmpty() = resultsHistory.isEmpty()

    fun prepareHistoryRecord(result: Double, height: String, weight: String, status: String, units: Pair<String, String>) {
        HistoryService.addHistoryRecord(
            Result(
                "$height ${units.first}", "$weight ${units.second}", "%.2f".format(result),
                SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date()), status
            )
        )
    }
}
