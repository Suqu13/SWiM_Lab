package com.example.swim_2

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.swim_2.fragments.DescriptionFragment
import com.example.swim_2.fragments.FamilyFragment
import com.example.swim_2.fragments.ImageFragment

class SecActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sec_activity_layout)
        val position = intent.extras!!.getInt("position")
        val images = intent.extras!!.getParcelableArrayList<Image>("images")!!
        launchFragment(position, images)
    }

    private fun launchFragment(position: Int, images: ArrayList<Image>){
        val imageFragment = ImageFragment.newInstance(images[position])
        val familyFragment = FamilyFragment.newInstance(images, position)
        val descriptionFragment = DescriptionFragment.newInstance(images[position])
        setFragmentsVisibility(imageFragment, descriptionFragment, familyFragment)
    }

    private fun setFragmentsVisibility(imageFragment: Fragment, descriptionFragment: Fragment, familyFragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().run {
            add(R.id.fragment_container, imageFragment)
            add(R.id.fragment_container, descriptionFragment)
            add(R.id.fragment_container, familyFragment)
            hide(descriptionFragment)
            hide(familyFragment)
            show(imageFragment)
            commit()
        }
    }
}