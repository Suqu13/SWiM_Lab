package com.example.swim_2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.swim_2.fragments.ImageFragment

class SecActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sec_activity_layout)
        launchFragment(intent.extras!!)
    }

    private fun launchFragment(bundle: Bundle){
        val fragment = ImageFragment.newInstance()
        fragment.arguments = bundle
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.addToBackStack(null)
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}