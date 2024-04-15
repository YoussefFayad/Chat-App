package com.example.chat_app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    val name: String? = null,
    val description: String? = null,
    val categoryId: String? = null,
    var id: String? = null

    ): Parcelable {
    companion object {
        val COLLECTION_NAME = "Rooms"
    }

}
