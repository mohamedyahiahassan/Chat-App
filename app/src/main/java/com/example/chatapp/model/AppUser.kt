package com.example.chatapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppUser(
    val uid: String? = null,
    val firstName: String? = null,
    val email: String? = null,
):Parcelable

{
    companion object {
        const val COLLECTION_NAME = "Users"

    }
}