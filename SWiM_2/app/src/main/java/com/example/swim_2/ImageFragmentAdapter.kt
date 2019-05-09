package com.example.swim_2

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image_fragment_recycle_view.view.*
import kotlin.collections.ArrayList


class ImageFragmentAdapter(private val images: ArrayList<Image>) : RecyclerView.Adapter<ImageFragmentAdapter.ImageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ImageHolder(inflater, parent)
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        Picasso.get()
            .load(images[holder.adapterPosition].imageUrl)
            .error(R.drawable.error)
            .into(holder.image)
    }

    class ImageHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.image_fragment_recycle_view, parent, false)) {
        val image: ImageView = itemView.image_rec_view
    }
}