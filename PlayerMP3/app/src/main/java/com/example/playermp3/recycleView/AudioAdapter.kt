package com.example.playermp3.recycleView

import android.media.MediaMetadataRetriever
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.recyclerview_audio.view.*
import com.bumptech.glide.request.RequestOptions
import com.example.playermp3.PlayerFragment
import com.example.playermp3.R
import com.example.playermp3.model.AudioFile
import com.example.playermp3.repo.AudioRepository

class AudioAdapter(private val myFragment: PlayerFragment, private val audioFilesList : MutableList<AudioFile> = AudioRepository.repository)
    : RecyclerView.Adapter<AudioAdapter.AudioHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_audio, parent, false)
        return AudioHolder(view)
    }

    override fun onBindViewHolder(holder: AudioHolder, position: Int) {
        holder.tittle.text = audioFilesList[position].title
        holder.author.text = audioFilesList[position].artist
        holder.album.text = audioFilesList[position].album
        loadEmbeddedCover(holder, position)
        holder.itemView.setOnClickListener{
            AudioRepository.currentAudio = position
            myFragment.initPlayingFromAdapter()
        }
    }

    private fun loadEmbeddedCover(holder: AudioHolder, position: Int) {
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(audioFilesList[position].path)
        val cover = mediaMetadataRetriever.embeddedPicture
        Glide.with(holder.itemView.context)
            .load(cover)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .apply(RequestOptions().override(200, 200))
            .error(R.drawable.music)
            .into(holder.cover)
    }


    override fun getItemCount() = audioFilesList.size

    class AudioHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tittle: TextView = itemView.manager_title
        val author: TextView = itemView.manager_artist
        val album: TextView = itemView.manager_album
        val cover : ImageView = itemView.holder_cover
    }
}