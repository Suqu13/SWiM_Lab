package com.example.swim_2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.swim_2.MainActivity.Companion.IMAGE_KEY
import com.example.swim_2.models.Image
import kotlinx.android.synthetic.main.add_image_activity.*

import java.util.*

class AddImageActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_image_activity)
        add_button.setOnClickListener {
            if (validate()) {
                Toast.makeText(this, R.string.image_added, Toast.LENGTH_SHORT).show()
                passImages()
            } else {
                Toast.makeText(this, R.string.fill_gaps, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun manageDate(): Date {
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar.time
    }

    private fun validate() = !(url_text_edit.text.isBlank() || name_text_edit.text.isBlank())

    private fun passImages() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(IMAGE_KEY, Image(url_text_edit.text.toString(), name_text_edit.text.toString(), manageDate()))
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}