package com.example.playermp3.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AudioFile (val path: String, val title: String, val artist: String, val album: String, val duration: Int): Parcelable