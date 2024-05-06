package com.example.chatapp.model

import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.log

object FireBaseUtils {

    val db = Firebase.firestore

    fun addUser(user: AppUser,
                onSuccessListener: OnSuccessListener<Void>,
                onFailureListener: OnFailureListener){

        db.collection("Users")
            .document("${user.uid}")
            .set(user)
            .addOnSuccessListener (onSuccessListener)
            .addOnFailureListener (onFailureListener)

    }

    fun getUser(uid:String,onSuccessListener: OnSuccessListener<DocumentSnapshot>,
                onFailureListener: OnFailureListener){

        db.collection("Users")
            .document(uid)
            .get()
            .addOnSuccessListener ( onSuccessListener)
            .addOnFailureListener ( onFailureListener)
    }

    fun createRoom(room:Room,
                   onSuccessListener: OnSuccessListener<Void>,
                   onFailureListener: OnFailureListener){

      val documentReference = db.collection("Rooms")
            .document()

            room.roomID = documentReference.id



       documentReference
            .set(room)
            .addOnSuccessListener (onSuccessListener)
            .addOnFailureListener ( onFailureListener )
    }

    // OnSuccessListener<DocumentSnapshot> and not OnSuccessListener<Void> because we need document snapshot
    fun getRooms(onSuccessListener: OnSuccessListener<QuerySnapshot>,
                 onFailureListener: OnFailureListener){

        db.collection("Rooms")
            .get()
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)
    }

    fun sendMessage(message: Message,
                    onSuccessListener: OnSuccessListener<Void>,
                    onFailureListener: OnFailureListener){

      val documentReference = Firebase.firestore.collection("Rooms")
            .document(message.roomId.toString())
            .collection(Message.COLLECTION_NAME)
            .document()

            documentReference
            .set(message)
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)

    }

    fun getMessage(room: Room,
                    eventListener: EventListener<QuerySnapshot>
                   ){

        db.collection("Rooms")
            .document(room.roomID?:"")
            .collection(Message.COLLECTION_NAME)
            .orderBy("dateTime", Query.Direction.ASCENDING)
            .addSnapshotListener(eventListener)


    }



}