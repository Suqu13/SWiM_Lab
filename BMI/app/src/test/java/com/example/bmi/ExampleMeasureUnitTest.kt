package com.example.bmi

import com.example.bmi.bmi.BmiForKgCm
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleMeasureUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun for_valid_data_should_count_bmi() {
        val bmi = BmiForKgCm(65.0, 170.0)
        assertEquals(22.491, bmi.countBmi(), 0.001) //0.001 to be sure that a result it is correct
    }

    @Test
    fun for_valid_data_should_count_bmi_1() {
        val bmi = BmiForKgCm(65.0, 170.0)
        assertEquals(22.491, bmi.countBmi(), 0.001) //0.001 to be sure that a result it is correct
    }
}
