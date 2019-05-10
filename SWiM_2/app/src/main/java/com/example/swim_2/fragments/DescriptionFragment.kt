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
        val image = arguments!!.getParcelable<Image>("image")!!
        setDescription(image, view)

        view.description_card_view.setOnClickListener{
            funReplaceFragments()
        }
        return view
    }

    private fun funReplaceFragments() {
        val manager = activity!!.supportFragmentManager
        manager.beginTransaction().run {
            setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            show(manager.fragments[0])
            hide(manager.fragments[1])
            hide(manager.fragments[2])
            commit()
        }
    }

        private fun setDescription(image: Image, view: View) {
            view.name_textViewF.text = image.name
            view.date_textViewF.text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(image.date)
            view.tags_textViewF.text = image.tags.joinToString(" #", prefix = "#")
    }

    companion object {
        @JvmStatic
        fun newInstance(image: Image) : DescriptionFragment{
            val bundle = Bundle()
            bundle.putParcelable("image", image)
            val fragment = DescriptionFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}