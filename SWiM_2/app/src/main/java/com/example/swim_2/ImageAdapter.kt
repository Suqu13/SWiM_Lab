package com.example.swim_2

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.drawable.Drawable
import android.graphics.Bitmap
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlin.collections.ArrayList


class ImageAdapter(private val images: ArrayList<Image>) : RecyclerView.Adapter<ImageAdapter.ImageHolder>() {

    private lateinit var target: Target

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.image_layout, parent, false)
        return ImageHolder(view)
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {

        target =  object: Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                holder.image.setImageBitmap(bitmap)
                FirebaseVision.getInstance().onDeviceImageLabeler
                    .processImage(FirebaseVisionImage.fromBitmap(bitmap!!))
                    .addOnSuccessListener {
                        holder.name.text = images[holder.adapterPosition].name
                        val tags = it.map {it.text}
                            .toTypedArray()
                            .take(3)
                        holder.tags.text = tags.joinToString(" #", prefix = "#")
                        images[holder.adapterPosition].tags = tags as ArrayList<String>
                    }
            }
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                holder.image.setImageResource(R.drawable.error)
                holder.tags.text = "#error"
                holder.name.text = "ERROR, be my unicorn"
            }

        }

        holder.date.text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(images[position].date)
        Picasso.get()
            .load(images[position].imageUrl)
            .into(target)
    }

    fun removeItem(position: Int) {
        images.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, images.size)
    }

    class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name_text_view)
        val date: TextView = itemView.findViewById(R.id.date_text_view)
        val tags: TextView = itemView.findViewById(R.id.tags_text_view)
        val image: ImageView = itemView.findViewById(R.id.image_view)
    }
}