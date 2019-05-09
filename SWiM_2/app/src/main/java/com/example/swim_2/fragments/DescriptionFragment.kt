package com.example.swim_2.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.swim_2.Image
import com.example.swim_2.R
import kotlinx.android.synthetic.main.fragment_description.view.*
import java.text.SimpleDateFormat
import java.util.*

class DescriptionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_description, container, false)
        setDescription(arguments!!.getParcelable("image")!!, view)
        return view
    }

    private fun setDescription(image: Image, view: View) {
            view.name_textViewF.text = image.name
            view.date_textViewF.text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(image.date)
            view.tags_textViewF.text = image.tags.joinToString(" #", prefix = "#")
    }

    companion object {
        @JvmStatic
        fun newInstance() = DescriptionFragment()
    }
}