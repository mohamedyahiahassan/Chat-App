package com.example.chatapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(

    val roomName : String? =null,
    var roomID:String? = null,
    val roomImage:Int? =null,
    val roomDesc:String? = null,
    val category: String?=null
):Parcelable
