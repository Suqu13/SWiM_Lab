package com.example.bmi


import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.*
import com.example.bmi.bmi.BmiForKgCm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toast_layout.view.*
import java.lang.Exception
import java.math.RoundingMode.CEILING
import java.text.DecimalFormat


class MainActivity : AppCompatActivity(){
    //type? (safe calls) - now myToast can be null, even if it is non-null references
    private var myToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createSpinner(R.id.hSpinner, R.array.height_units)
        createSpinner(R.id.wSpinner, R.array.weight_units)
        myToast = Toast(applicationContext)
        countBtn.setOnClickListener {
            val val1 = getResult()
            val val2 = analyzeResult(val1)
            showToast(val2)
        }
    }

    private fun createSpinner(mySpinner: Int, myArray: Int) {
        val spinner: Spinner = findViewById(mySpinner)
        ArrayAdapter.createFromResource(
            this,
            myArray,
            android.R.layout.simple_spinner_item
        ) .also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        //TODO: try to add listeners to spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }

        }
    }

    private fun getResult(): Pair<Boolean, Double> {
        if(!heightNum.text.isBlank() && !weightNum.text.isBlank()) {
            val height = heightNum.text.toString().toDouble()
            val weight = weightNum.text.toString().toDouble()
            if (height != 0.0 && weight != 0.0) {
                return Pair(true, BmiForKgCm(weight, height).countBmi())
            }
        }
        return Pair(false, -1.0)
    }

    private fun analyzeResult(toAnalyze: Pair<Boolean, Double>): Triple<Int, Int, String> {
        val (correctness, result) = toAnalyze
        val roundedFormat = DecimalFormat("#.#")
        roundedFormat.roundingMode = CEILING
        return when(correctness) {
            true -> {
                when (val roundedRes = roundedFormat.format(result).toDouble()) {
                    // in num .. difNum - means that we are working on range: num <= ourValue && ourValue <= difNum
                    in Double.MIN_VALUE..18.4 -> Triple(R.drawable.ic_toast_neutral_24dp, resources.getColor(R.color.blue), "UNDERWEIGHT, $roundedRes")
                    in 18.5..24.9 -> Triple(R.drawable.ic_toast_very_happy, resources.getColor(R.color.shittyGreen), "NORMAL, $roundedRes")
                    in 25.0..29.9 -> Triple(R.drawable.ic_toast_neutral_24dp, resources.getColor(R.color.orange), "OVERWEIGHT, $roundedRes")
                    in 30.0..34.9 -> Triple(R.drawable.ic_toast_sad_24dp, resources.getColor(R.color.red), "OBESE, $roundedRes")
                    else -> Triple(R.drawable.ic_toast_very_dissatisfied_24dp, resources.getColor(R.color.violet), "EXTREMELY OBESE, $roundedRes")
                }
            }
            else -> {
                shakeThatPinata()
                Triple(R.drawable.ic_toast_error_24dp, resources.getColor(R.color.black), "FILL THE GAPS")
            }
        }
    }

    private fun showToast(toShow: Triple<Int, Int, String>) {
        val (iconResource, colorValue, message) = toShow
        //!! (not-null assertion) - converts any value to a non-null type and throws an exception if the value is null
        try {
            val layout: View = layoutInflater.inflate(R.layout.toast_layout, findViewById(R.id.toast_root))
            myToast!!.setGravity(0, 0, 0)
            myToast!!.duration = Toast.LENGTH_LONG
            myToast!!.view = layout
            (myToast!!.view.findViewById(R.id.text_view) as TextView).text = message
            myToast!!.view.setBackgroundColor(colorValue)
            myToast!!.view.image_view.setImageResource(iconResource)
            myToast!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun shakeThatPinata() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((getSystemService(VIBRATOR_SERVICE)) as Vibrator).vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            (( getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(200))
        }
    }

    override fun onStop() {
        myToast!!.cancel()
        super.onStop()
    }

}
