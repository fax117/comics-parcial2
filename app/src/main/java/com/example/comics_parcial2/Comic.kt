package com.example.comics_parcial2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comic(
    val title: String,
    val description: String,
    val picture: String,
    val cost: Int,
): Parcelable
