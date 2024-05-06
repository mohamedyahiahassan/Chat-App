package com.example.chatapp.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.model.FireBaseUtils
import com.example.chatapp.model.Room
import com.google.firebase.firestore.toObject

class HomeViewModel : ViewModel() {

    var navigator : HomeNavigator? = null

    val roomsList = mutableStateListOf<Room>()



    fun getRooms(){

        FireBaseUtils.getRooms(
            onSuccessListener =

            {

            it.forEach {
                val room = it.toObject(Room::class.java)
                if (room !=null) {
                    roomsList.add(room)
                }
            }
                Log.e("Tag",roomsList.size.toString())

                roomsList.forEach{

                    Log.e("Tag","${it?.roomName} + ${it?.roomID}")
                }

        }, onFailureListener = {

            Log.e("Tag",it.message.toString())

            })
    }


}


interface HomeNavigator {

    fun navigateToAddRoomActivity()

    fun navigateToChat(room:Room)
}