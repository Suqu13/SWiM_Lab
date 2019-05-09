package com.example.swim_2.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.swim_2.Image
import com.example.swim_2.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_image.view.*
import java.text.FieldPosition

class ImageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_image, container, false)
        val images = arguments!!.getParcelableArrayList<Image>("images")
        val position : Int = arguments!!.getInt("position")

        loadImage(images!![position], view)

        view.imageView.setOnClickListener{
           funReplaceFragments(images, position)
        }
        return view
    }

    private fun funReplaceFragments(images: ArrayList<Image>, position: Int) {
        val descriptionFragment = DescriptionFragment.newInstance()
        val bundleDescription = Bundle()
        bundleDescription.putParcelableArrayList("images", images )
        bundleDescription.putInt("position", position)
        descriptionFragment.arguments = bundleDescription

        val familyFragment = FamilyFragment.newInstance()
        val bundleFamily= Bundle()
        bundleFamily.putParcelableArrayList("images", images )
        bundleFamily.putInt("position", position)
        familyFragment.arguments = bundleFamily

        fragmentManager!!.beginTransaction().run {
            replace(R.id.fragment_container, descriptionFragment)
            add(R.id.fragment_container, familyFragment)
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
        fun newInstance() = ImageFragment()
    }
}
