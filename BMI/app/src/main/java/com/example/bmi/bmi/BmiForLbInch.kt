package com.example.bmi.bmi

class BmiForLbInch (private val weight: Double, private val height: Double) : Bmi {
        override fun countBmi() = weight *0.45359237*10000.0 / (height*height*2.54*2.54)
}