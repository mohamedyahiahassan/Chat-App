package com.example.chatapp.addRoom

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.model.Category
import com.example.chatapp.model.FireBaseUtils
import com.example.chatapp.model.Room

class AddRoomViewModel : ViewModel(){

    val roomNameState = mutableStateOf<String>("")
    val roomNameErrorState = mutableStateOf<String?>(null)

    val roomDescState = mutableStateOf<String>("")
    val roomDescErrorState = mutableStateOf<String?>(null)

    val dropDownMenuState = mutableStateOf<Boolean>(false)

    val categoryList = Category.getCategoriesList()
    val selectedCategoryItem = mutableStateOf(categoryList.get(0))

    var addRoomNavigator : Navigation? = null



    fun addRoom(){

        if (validateFields()){
            FireBaseUtils.createRoom(
                Room(
                   roomName =  roomNameState.value,
                    roomID = null,
                    roomImage = selectedCategoryItem.value.image,
                   roomDesc = roomDescState.value,
                   category = selectedCategoryItem.value.name),
                {

                addRoomNavigator?.navigateToHome()


                },{
                    Log.e("Tag",it.message.toString())
                })

        }
    }

     fun validateFields():Boolean{

         if (roomNameState.value.isNullOrEmpty()){

             roomNameErrorState.value = "Required"
             return false
         } else{

             roomNameErrorState.value = null
         }

         if (roomDescState.value.isNullOrEmpty()){

             roomDescErrorState.value = "Required"
             return false
         } else{

             roomDescErrorState.value = null
         }

         return true
    }

}

interface Navigation {

    fun navigateToHome()
    fun navigateUp()

}