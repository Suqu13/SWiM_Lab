package com.example.playermp3.services

object TimeConverterService {
    fun convertTime(millis: Int): String {
        var sec = millis / 1000
        val min = sec / 60
        sec %= 60
        if (sec < 10) return "$min:0$sec"
        return "$min:$sec"
    }
}