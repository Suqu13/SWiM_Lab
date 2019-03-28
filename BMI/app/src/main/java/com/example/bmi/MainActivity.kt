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
import com.example.bmi.history.HistoryActivity
import com.example.bmi.services.HistoryService
import com.example.bmi.services.SharedPreferencesService
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){

    private var imperialUnitsSwitch = false
    private var isCounted = false
    private var bmiResultDescription: String? = ""

    companion object {
        const val STATUS_KEY = "status"
        const val RESULT_KEY = "result"
        const val VISIBLE_KEY = "visible"
        const val DESCRIPTION_KEY = "description"
        const val UNITS_SWITCH_KEY = "unitsSwitch"
        const val COLOR_KEY = "color"
        const val BMI_HISTORY_KEY = "bmiHistory"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SharedPreferencesService.createSharedPref(this)
        height_edit_text.setHint(R.string.provide_height)
        weight_editText.setHint(R.string.provide_weight)
        count_button.setOnClickListener {
            showFinalResult(validateData())
        }
        info_button.setOnClickListener {
            launchInfoActivity()
        }
    }

    override fun onResume() {
        isCounted = false
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu!!.findItem(R.id.history).isVisible = !HistoryService.isHistoryEmpty()
        menu.findItem(R.id.imperial_units).isChecked = imperialUnitsSwitch
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId){
            R.id.imperial_units -> onUnitsChange(item)
            R.id.history -> launchHistoryActivity()
            R.id.about_me -> launchAboutActivity()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun onUnitsChange(item: MenuItem) {
        imperialUnitsSwitch = !imperialUnitsSwitch
        item.isChecked = imperialUnitsSwitch
        bmi_container.visibility = View.INVISIBLE
        height_edit_text.text.clear()
        weight_editText.text.clear()
        unitsChange()
    }

    private fun unitsChange() {
        findViewById<TextView>(R.id.height_textView).setText(if (imperialUnitsSwitch) R.string.height_inch else R.string.height_cm)
        findViewById<TextView>(R.id.weight_textView).setText(if (imperialUnitsSwitch) R.string.weight_lb else R.string.weight_kg)
    }

    private fun launchHistoryActivity() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }

    private fun launchAboutActivity() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    private fun launchInfoActivity() {
        val bundle = Bundle()
        bundle.run {
            putString(RESULT_KEY, findViewById<TextView>(R.id.bmi_value_textView).text.toString())
            putString(STATUS_KEY, findViewById<TextView>(R.id.bmi_status_textView).text.toString())
            putString(DESCRIPTION_KEY, bmiResultDescription)
        }
        val intent = Intent(this, InfoActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun countWithRightUnits(height: Double, weight: Double ): Double {
        return if (imperialUnitsSwitch)
            BmiForLbInch(weight, height).countBmi()
        else
            BmiForKgCm(weight, height).countBmi()
    }

    private fun reactForInvalidData() {
        if (bmi_container.visibility == View.VISIBLE)
            bmi_container.visibility = View.INVISIBLE
        shakeThatPinata()
    }


    private fun validateData(): Double {
        var validData = true
        if (height_edit_text.run{text.isBlank() || text.toString().toDouble() > 400}) {
            height_edit_text.error = getString(R.string.invalid_height_message)
            validData = false
        }
        if (weight_editText.run{text.isBlank() || text.toString().toDouble() > 1500}) {
            weight_editText.error = getString(R.string.invalid_weight_message)
            validData = false
        }
        if(validData) {
            val result = countWithRightUnits(height_edit_text.text.toString().toDouble(), weight_editText.text.toString().toDouble())
            if (result < 300 && result > 5) {
                isCounted = true
                return result
            }
            Toast.makeText(this, getString(R.string.invalid_result_message), Toast.LENGTH_SHORT).show()
        }
        reactForInvalidData()
        return -1.0
    }

    private fun analyzeResult(toAnalyze: Double) = when {
        toAnalyze < 18.5 -> Triple(getString(R.string.underweight_status), ContextCompat.getColor(applicationContext, R.color.lapis_lazuli), getString(R.string.underweight_description))
        toAnalyze < 25.0 -> Triple(getString(R.string.normal_status),  ContextCompat.getColor(applicationContext, R.color.verdigris), getString(R.string.normal_description))
        toAnalyze < 30.0  -> Triple(getString(R.string.overweight_status), ContextCompat.getColor(applicationContext, R.color.orange), getString(R.string.overweight_description))
        toAnalyze < 35.0  -> Triple(getString(R.string.obese_status),  ContextCompat.getColor(applicationContext,  R.color.pompeian_roses), getString(R.string.obese_description))
        else -> Triple(getString(R.string.extremely_obese_status),  ContextCompat.getColor(applicationContext,  R.color.violet), getString(R.string.extremely_obese_description))
    }

    private fun showFinalResult(toAnalyze: Double) {
        val bmiContainer: ConstraintLayout = findViewById(R.id.bmi_container)
        when {
            toAnalyze != -1.0 -> {
                val resToAnalyze = Math.round(toAnalyze * 100) / 100.0
                val (status, color, info) = analyzeResult(resToAnalyze)
                if (bmiContainer.visibility == View.INVISIBLE)
                    bmiContainer.visibility = View.VISIBLE
                changeResultTextViewsAttributes(resToAnalyze, status, color, info)
                HistoryService.prepareHistoryRecord(resToAnalyze, height_edit_text.text.toString(), weight_editText.text.toString(), status, imperialUnitsSwitch)
            }
        }
    }

    private fun changeResultTextViewsAttributes(result: Double, status: String, color: Int, description: String) {
        val resultText = "%.2f".format(result)
        bmi_value_textView.run {
            text = resultText
            setTextColor(color)
        }
        bmi_status_textView.text = status
        bmiResultDescription = description
    }


    //Function enables using vibration on a phone to signal invalid inputs
    private fun shakeThatPinata() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((getSystemService(VIBRATOR_SERVICE)) as Vibrator).vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            (( getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(200))
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.run {
            putString(RESULT_KEY, bmi_value_textView.text.toString())
            putString(STATUS_KEY, bmi_status_textView.text.toString())
            putInt(COLOR_KEY, bmi_value_textView.currentTextColor)
            putBoolean(UNITS_SWITCH_KEY, imperialUnitsSwitch)
            putString(DESCRIPTION_KEY, bmiResultDescription)
            putInt(VISIBLE_KEY, bmi_container.visibility)
        }
        if (isCounted) {
            SharedPreferencesService.removeKeyValue(BMI_HISTORY_KEY)
            SharedPreferencesService.commitHistoryChanges(HistoryService.getResultsHistory())
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        imperialUnitsSwitch = savedInstanceState!!.getBoolean(UNITS_SWITCH_KEY)
        if(imperialUnitsSwitch)
            invalidateOptionsMenu()
        if ((savedInstanceState.getInt(VISIBLE_KEY)) != View.INVISIBLE) {
            bmi_container.visibility = savedInstanceState.getInt(VISIBLE_KEY)
            bmi_value_textView.text = savedInstanceState.getString(RESULT_KEY)
            bmi_status_textView.text = savedInstanceState.getString(STATUS_KEY)
            bmi_value_textView.setTextColor(savedInstanceState.getInt(COLOR_KEY))
            bmiResultDescription = savedInstanceState.getString(DESCRIPTION_KEY)
        }
        unitsChange()
        super.onRestoreInstanceState(savedInstanceState)
    }

    /**But in android spec they are writing about saving data in onPause(). In low memory situation onStop() can be
    never called, there is no enough memory to keep the activity's process running.**/
//    override fun onPause() {
//        if (isCounted) {
//            SharedPreferencesService.clearData()
//            SharedPreferencesService.commitHistoryChanges(HistoryService.getResultsHistory())
//        }
//        super.onPause()
//    }
}
