package com.example.bmi

import android.widget.EditText

fun EditText.validate(min: Double, max: Double, errorMessage: Pair<String, String>): Double? {
    if (!this.text.isBlank()) {
        val value = this.text.toString().toDouble()
        if (value < min || max < value) {
            this.error = errorMessage.first
            return null
        }
        return value
    }
    this.error = errorMessage.second
    return null
}
