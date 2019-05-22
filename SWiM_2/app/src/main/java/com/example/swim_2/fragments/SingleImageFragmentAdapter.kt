package com.example.swim_2.fragments

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.example.swim_2.R
import com.example.swim_2.models.Image
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_similar_images_recycler_view.view.*
import kotlin.collections.ArrayList


class SingleImageFragmentAdapter(private val images: ArrayList<Image>) : RecyclerView.Adapter<SingleImageFragmentAdapter.ImageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        return ImageHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        Picasso.get()
            .load(images[holder.adapterPosition].imageUrl)
            .error(R.drawable.error)
            .into(holder.image)
    }

    class ImageHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.fragment_similar_images_recycler_view, parent, false)) {
        val image: ImageView = itemView.image_rec_view
    }
}