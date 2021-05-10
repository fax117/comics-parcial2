package com.example.comics_parcial2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comic(
    val picture:Int,
    val title:String,
    val release_year: String,
    val bio: String): Parcelable
