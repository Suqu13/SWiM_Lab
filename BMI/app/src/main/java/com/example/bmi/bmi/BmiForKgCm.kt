package com.example.bmi.bmi

import com.example.bmi.units.measureUnit

class BmiForKgCm(private val weight: Double, private val height: Double, private val hFactor: measureUnit = measureUnit.CENTIMETER,
                 private val wFactor: measureUnit = measureUnit.KILO) : Bmi {

//    override fun countBmi(): Double {
//        val bmi = weight *10000.0 / (height*height)
//        return bmi
//    }

        override fun countBmi() = weight * wFactor.factor *10000.0 / (height*height*hFactor.factor*hFactor.factor)

}