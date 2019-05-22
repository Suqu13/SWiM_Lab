package com.example.swim_2.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class Image(val imageUrl: String, val name: String, val date: Date, var tags: ArrayList<String> = ArrayList()) : Parcelable

