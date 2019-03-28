package com.example.bmi

object HistoryService {
    private  var resultsHistory: ArrayList<Result> = SharedPreferencesService.loadData()
    private var maxSize: Int = 10

    fun addHistoryRecord(result: Result) {
        if(resultsHistory.size == maxSize)
            resultsHistory.removeAt(maxSize - 1)
        resultsHistory.add(0, result)

    }

    fun getResultsHistory() = resultsHistory
}
