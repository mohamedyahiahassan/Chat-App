package com.example.chatapp.chatRoom

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.model.FireBaseUtils
import com.example.chatapp.model.Message
import com.example.chatapp.model.Room
import com.example.chatapp.model.User
import com.google.firebase.firestore.DocumentChange

import com.google.firebase.firestore.toObject
import java.util.Date

class ChatActivityViewModel : ViewModel(){

    val messageTextState = mutableStateOf<String>("")

    var room :Room? = null

   val messagesList = mutableStateListOf<Message>()

   val user = User.user



    fun sendMessage(){

        if(messageTextState.value.isEmpty()||messageTextState.value.isBlank()) return

        val message = Message(
            id = null,
            senderName = user!!.firstName,
            senderId = user.uid,
            content = messageTextState.value,
            roomId = room?.roomID,
            dateTime = Date().time

        )

       FireBaseUtils.sendMessage(
            message,
            onSuccessListener = {
            messageTextState.value = ""

        },
            onFailureListener = {
                Log.e("Tag",it.message.toString())
            })




    }

    fun listenToMessageChange(room: Room){

        FireBaseUtils.getMessage(
            room,

           eventListener = { querySnapShot,exception ->

               if (exception!=null){
                   Log.w("TAG","listen:error",exception)
             return@getMessage
               }

               for (dc in querySnapShot!!.documentChanges) {
                   when (dc.type) {
                       DocumentChange.Type.ADDED -> {
                           messagesList.add(dc.document.toObject(Message::class.java))
                       }

                       else -> {}
                   }
               }


           }

        )
    }


}