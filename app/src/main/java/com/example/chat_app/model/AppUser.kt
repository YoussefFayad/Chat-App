package com.example.chat_app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class AppUser(
    val uid :String?= null,
    val firstName :String?= null,
    val email :String?= null,

): Parcelable {
    companion object{
        val COLLECTION_NAME = "users"
    }
}
