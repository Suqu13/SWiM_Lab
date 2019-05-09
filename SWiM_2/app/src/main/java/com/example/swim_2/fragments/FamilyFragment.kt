package com.example.swim_2.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.swim_2.Image
import com.example.swim_2.ImageFragmentAdapter
import com.example.swim_2.R
import kotlinx.android.synthetic.main.fragment_family.*

class FamilyFragment : Fragment() {

    private lateinit var toShow : ArrayList<Image>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_family, container, false)
        toShow = getTheMostSimilar(
            arguments!!.getParcelableArrayList<Image>("images"),
            arguments!!.getInt("position")
        ).map {it.first} as ArrayList<Image>
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragment_recycle_view.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = ImageFragmentAdapter(toShow)
        }
    }

    private fun getTheMostSimilar(images: ArrayList<Image>?, position: Int) : List<Pair<Image, () -> Int>>{
        val tags = images!![position].tags
        val newImagesList = images.filter { images.indexOf(it) != position }
        return newImagesList.map{ img->Pair(img, { tags.filter{ img.tags.contains(it) }.count() } ) }
            .sortedWith(compareBy { it.second() }).filter { it.second() > 0 }.takeLast(6)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FamilyFragment()
    }
}
