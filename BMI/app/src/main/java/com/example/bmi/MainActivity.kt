package com.example.bmi


import android.content.Intent
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.bmi.bmi.BmiForKgCm
import com.example.bmi.bmi.BmiForLbInch
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(){

    //type? (safe calls)
    private var imperialUnitsSwitch = false
    private var bmiResultDescription: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SharedPreferencesService.createSharedPref(this)
        ResultsHistory.initializeResultHistory(SharedPreferencesService.loadData())
        SharedPreferencesService.clearData()
        countBtn.setOnClickListener {
            analyzeResult(validateData())
        }
        infoBtn.setOnClickListener {
            launchInfoActivity()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId){
            R.id.imperial_units -> onUnitsChange(item)
            R.id.history -> {
                launchHistoryActivity()
            }
            R.id.about_me -> launchAboutActivity()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun onUnitsChange(item: MenuItem) {
        if (bmiContainer.visibility == View.VISIBLE) {
            bmiContainer.visibility = View.INVISIBLE
        }
        heightNum.text.clear()
        weightNum.text.clear()

        imperialUnitsSwitch = !imperialUnitsSwitch
        item.isChecked = imperialUnitsSwitch
        unitsChange()
    }

    private fun unitsChange() {
        val myTextViewH: TextView = findViewById(R.id.heightText)
        val myTextViewW: TextView = findViewById(R.id.weightText)
        if(imperialUnitsSwitch) {
            myTextViewH.setText(R.string.heightInch)
            myTextViewW.setText(R.string.weightLb)
        } else {
            myTextViewH.setText(R.string.heightCm)
            myTextViewW.setText(R.string.weightKg)
        }
    }

    private fun launchHistoryActivity() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)    }

    private fun launchAboutActivity() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    private fun launchInfoActivity() {
        val intent = Intent(this, InfoActivity::class.java)
        val bundle = Bundle()
        bundle.putString("result", findViewById<TextView>(R.id.bmiNumber).text.toString())
        bundle.putString("status", findViewById<TextView>(R.id.bmiStatus).text.toString())
        bundle.putString("description", bmiResultDescription)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun countForRightUnits(height: Double, weight: Double ): Double {
        return if (imperialUnitsSwitch)
            BmiForLbInch(weight, height).countBmi()
        else
            BmiForKgCm(weight, height).countBmi()
    }

    private fun reactForInvalidData() {
        if (bmiContainer.visibility == View.VISIBLE)
            bmiContainer.visibility = View.INVISIBLE
        bmiResultDescription = null
        shakeThatPinata()
    }


    private fun validateData(): Double {
        var height = 0.0
        var weight = 0.0
        var validData = true
        if (heightNum.text.isBlank()) {
            heightNum.error = "Provide height!"
            validData = false
        } else {
            height = heightNum.text.toString().toDouble()
        }
        if (weightNum.text.isBlank()) {
            weightNum.error = "Provide weight!"
            validData = false
        } else {
            weight = weightNum.text.toString().toDouble()
        }
        if(validData) {
            val result = countForRightUnits(height, weight)
            if (result < 300 && result > 5) {
                return result
            }
            Toast.makeText(this, "Are you sure that you provided right data?", Toast.LENGTH_SHORT).show()
        }
        reactForInvalidData()
        return -1.0
    }

    //Function analyzes the result and returns Triple which contains data needed to create custom toast
    private fun analyzeResult(toAnalyze: Double) {
        val bmiContainer: ConstraintLayout = findViewById(R.id.bmiContainer)
        when {
            toAnalyze != -1.0 -> {
                val resToAnalyze = Math.round(toAnalyze * 100) / 100.0
                val (status, color, info) = when {
                        resToAnalyze < 18.5 -> Triple("UNDERWEIGHT", ContextCompat.getColor(applicationContext, R.color.lapis_lazuli), getString(R.string.underweight_info))
                        resToAnalyze < 25.0 -> Triple("NORMAL",  ContextCompat.getColor(applicationContext, R.color.verdigris), getString(R.string.normal_info))
                        resToAnalyze < 30.0  -> Triple("OVERWEIGHT", ContextCompat.getColor(applicationContext, R.color.orange), getString(R.string.overweight_info))
                        resToAnalyze < 35.0  -> Triple("OBESE",  ContextCompat.getColor(applicationContext,  R.color.pompeian_roses), getString(R.string.obese_info))
                        else -> Triple("EXTREMELY OBESE",  ContextCompat.getColor(applicationContext,  R.color.violet), getString(R.string.extremely_obese_info_info))
                    }
                if (bmiContainer.visibility == View.INVISIBLE)
                    bmiContainer.visibility = View.VISIBLE
                changeResultAttributes(resToAnalyze, status, color, info)
               val (weightU, heightU) = if (imperialUnitsSwitch) {
                   Pair("[lbs]","[inches]")
                } else {
                   Pair("[kg]","[cm]")
               }
                ResultsHistory.addResultHistoryRecord(Result(heightNum.text.toString() + " " + heightU,
                    weightNum.text.toString() + " " + weightU, resToAnalyze.toString(),
                    SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date()), status))
                SharedPreferencesService.clearData()
                SharedPreferencesService.commitChanges(ResultsHistory.getResultsHistory())
            }
        }
    }

    private fun changeResultAttributes(result: Double, status: String, color: Int, description: String) {
        val bmiNumber: TextView = findViewById(R.id.bmiNumber)
        val bmiStatus: TextView = findViewById(R.id.bmiStatus)
        val resultText = "%.2f".format(result)
        bmiNumber.text = resultText
        bmiStatus.text = status
        bmiNumber.setTextColor(color)
        bmiResultDescription = description
    }


    //Function enables using vibration on a phone
    private fun shakeThatPinata() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((getSystemService(VIBRATOR_SERVICE)) as Vibrator).vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            (( getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(200))
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putString("result", bmiNumber.text.toString())
        outState.run {
            putString("status", bmiStatus.text.toString())
            putInt("color", bmiNumber.currentTextColor)
            putBoolean("unitsSwitch", imperialUnitsSwitch)
            putString("description", bmiResultDescription)
            putInt("visible", bmiContainer.visibility)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        imperialUnitsSwitch = savedInstanceState!!.getBoolean("unitsSwitch")
        if(imperialUnitsSwitch)
            invalidateOptionsMenu()
        if ((savedInstanceState.getInt("visible")) != View.INVISIBLE) {
            bmiContainer.visibility = savedInstanceState.getInt("visible")
            bmiNumber.text = savedInstanceState.getString("result")
            bmiStatus.text = savedInstanceState.getString("status")
            bmiNumber.setTextColor(savedInstanceState.getInt("color"))
            bmiResultDescription = savedInstanceState.getString("description")
        }
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.imperial_units)?.isChecked = imperialUnitsSwitch
        if(imperialUnitsSwitch) {
            findViewById<TextView>(R.id.heightText).setText(R.string.heightInch)
            findViewById<TextView>(R.id.weightText).setText(R.string.weightLb)
        }
        return super.onPrepareOptionsMenu(menu)
    }
}
