package com.example.comics_parcial2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comic(
    val id: String,
    val title: String,
    val description: String,
    val picture: String,
    val cost: Double,
): Parcelable{
    constructor():this("","","","",0.0)
}

