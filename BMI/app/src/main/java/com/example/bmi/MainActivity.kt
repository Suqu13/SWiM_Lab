package com.example.bmi


import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.widget.*
import com.example.bmi.bmi.BmiForKgCm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toast_layout.view.*
import java.lang.Exception


class MainActivity : AppCompatActivity(){

    //type? (safe calls) - now myToast can be null, even if it is non-null references
    private var myToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myToast = Toast(applicationContext)
        countBtn.setOnClickListener {
            val val1 = validateData()
            val val2 = analyzeResult(val1)
            showToast(val2)
        }
    }

    //Function validates inputs and returns result or -1.0
    private fun validateData(): Double {
        if(!heightNum.text.isBlank() && !weightNum.text.isBlank()) {
            val height = heightNum.text.toString().toDouble()
            val weight = weightNum.text.toString().toDouble()
            if (height > 0.0 && weight > 0.0) {
                return  BmiForKgCm(weight, height).countBmi()
            }
        }
        return -1.0
    }

    //Function analyzes the result and returns Triple which contains data needed to create custom toast
    private fun analyzeResult(toAnalyze: Double): Triple<Int, Int, String> {
        return when {
            toAnalyze != -1.0 -> {
                val resToAnalyze = Math.round(toAnalyze * 100) / 100.0
                when {
                    resToAnalyze < 18.5 -> Triple(R.drawable.ic_toast_neutral_24dp, ContextCompat.getColor(applicationContext ,R.color.blue), "UNDERWEIGHT, $resToAnalyze")
                    resToAnalyze < 25.0 -> Triple(R.drawable.ic_toast_very_happy, ContextCompat.getColor(applicationContext ,R.color.shittyGreen), "NORMAL, $resToAnalyze")
                    resToAnalyze < 30.0  -> Triple(R.drawable.ic_toast_neutral_24dp, ContextCompat.getColor(applicationContext ,R.color.orange), "OVERWEIGHT, $resToAnalyze")
                    resToAnalyze < 35.0  -> Triple(R.drawable.ic_toast_sad_24dp, ContextCompat.getColor(applicationContext ,R.color.red), "OBESE, $resToAnalyze")
                    else -> Triple(R.drawable.ic_toast_very_dissatisfied_24dp, ContextCompat.getColor(applicationContext ,R.color.violet), "EXTREMELY OBESE, $resToAnalyze")
                }
            }
            else -> {
                shakeThatPinata()
                Triple(R.drawable.ic_toast_error_24dp, ContextCompat.getColor(applicationContext ,R.color.black), "FILL THE GAPS")
            }
        }
    }

    //Function sets attributes of toast like text, background color and icon,
    private fun showToast(toShow: Triple<Int, Int, String>) {
        val (iconResource, colorValue, message) = toShow
        //!! (not-null assertion) - converts any value to a non-null type and throws an exception if the value is null
        try {
            val layout: View = layoutInflater.inflate(R.layout.toast_layout, findViewById(R.id.toast_root))
            myToast!!.setGravity(Gravity.BOTTOM, 0, 330)
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

    //Function enable using vibration on a phone
    private fun shakeThatPinata() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((getSystemService(VIBRATOR_SERVICE)) as Vibrator).vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            (( getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(200))
        }
    }

    //Function is overriden to kill the toast when user stops the whole app
    override fun onStop() {
        myToast!!.cancel()
        super.onStop()
    }

}
