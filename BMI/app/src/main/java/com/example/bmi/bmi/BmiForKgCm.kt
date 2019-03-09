package com.example.bmi.bmi

class BmiForKgCm(private val weight: Double, private val height: Double) : Bmi {
        override fun countBmi() = weight *10000.0 / (height*height)
}