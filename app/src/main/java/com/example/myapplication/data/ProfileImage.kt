package com.example.myapplication.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileImage(
    val large: String?,
    val medium: String?,
    val small: String?
): Parcelable