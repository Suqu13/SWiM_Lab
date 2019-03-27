package com.example.bmi

object ResultsHistory {
    private  var resultsHistory: ArrayList<Result> = ArrayList()
    private var maxSize: Int = 10

    fun initializeResultHistory(newData: ArrayList<Result>) {
        resultsHistory = newData
    }

    fun setMaxSize(newMaxSize: Int) {
        maxSize = newMaxSize
    }

    fun getMaxSize() = maxSize

    fun addResultHistoryRecord(result: Result) {
        if(resultsHistory.size == maxSize)
            resultsHistory.removeAt(maxSize - 1)
        resultsHistory.add(0, result)

    }

    fun getResultsHistory() = resultsHistory
}
