package com.example.swim_2.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.swim_2.MainActivity.Companion.IMAGE_KEY
import com.example.swim_2.models.Image
import com.example.swim_2.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_image.view.*

class SingleImageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_image, container, false)
        val image = arguments!!.getParcelable<Image>(IMAGE_KEY)!!
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
        fun newInstance(image: Image) : SingleImageFragment {
            val bundle = Bundle()
            bundle.putParcelable(IMAGE_KEY, image)
            val fragment = SingleImageFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
