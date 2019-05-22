package com.example.swim_2

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.swim_2.MainActivity.Companion.IMAGES_KEY
import com.example.swim_2.MainActivity.Companion.POSITION_KEY
import com.example.swim_2.fragments.ImageDescriptionFragment
import com.example.swim_2.fragments.SimilarImagesFragment
import com.example.swim_2.fragments.SingleImageFragment
import com.example.swim_2.models.Image

class FragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        val position = intent.extras!!.getInt(POSITION_KEY)
        val images = intent.extras!!.getParcelableArrayList<Image>(IMAGES_KEY)!!
        launchFragment(position, images)
    }

    private fun launchFragment(position: Int, images: ArrayList<Image>){
        val imageFragment = SingleImageFragment.newInstance(images[position])
        val familyFragment = SimilarImagesFragment.newInstance(images, position)
        val descriptionFragment = ImageDescriptionFragment.newInstance(images[position])
        setFragmentsVisibility(imageFragment, descriptionFragment, familyFragment)
    }

    private fun setFragmentsVisibility(imageFragment: Fragment, descriptionFragment: Fragment, familyFragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().run {
            add(R.id.fragment_container, imageFragment)
            add(R.id.fragment_container, descriptionFragment)
            add(R.id.fragment_container, familyFragment)
            show(imageFragment)
            hide(descriptionFragment)
            hide(familyFragment)
            commit()
        }
    }
}