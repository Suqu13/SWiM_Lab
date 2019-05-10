package com.example.swim_2.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.swim_2.Image
import com.example.swim_2.R
import com.example.swim_2.SecActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_image.view.*
import java.text.FieldPosition

class ImageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_image, container, false)
        val image = arguments!!.getParcelable<Image>("image")!!

        loadImage(image, view)

        view.imageView.setOnClickListener{
           funReplaceFragments()
        }
        return view
    }

    private fun funReplaceFragments() {
        val manager = activity!!.supportFragmentManager
        manager!!.beginTransaction().run {
            setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            hide(manager.fragments[0])
            show(manager.fragments[1])
            show(manager.fragments[2])
            commit()
        }
    }

    private fun loadImage(image: Image, view: View){
            Picasso.get()
                .load(image.imageUrl)
                .error(R.drawable.error)
                .into(view.imageView)
    }

    companion object {
        @JvmStatic
        fun newInstance(image: Image) : ImageFragment {
            val bundle = Bundle()
            bundle.putParcelable("image", image)
            val fragment = ImageFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
