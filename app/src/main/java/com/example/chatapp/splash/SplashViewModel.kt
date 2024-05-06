package com.example.chatapp.splash

import androidx.lifecycle.ViewModel
import com.example.chatapp.model.AppUser
import com.example.chatapp.model.FireBaseUtils
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.toObject


class SplashViewModel : ViewModel() {

    val auth = Firebase.auth

    var splashNavigator : Navigator? = null

   fun navigate(){

       if (auth.currentUser!=null){
           getUserFromFireStore()
       }else{

           splashNavigator?.navigateToLogin()
       }
   }

    fun getUserFromFireStore(){
        FireBaseUtils.getUser(auth.currentUser?.uid!!,
            onSuccessListener = {
             val user = it.toObject(AppUser::class.java)
                if (user != null) {
                    com.example.chatapp.model.User.user = user
                    splashNavigator?.navigateToHome(user)
                }
            },
            onFailureListener = {
                splashNavigator?.navigateToLogin()
            })
    }


}

interface Navigator{

    fun navigateToHome(user: AppUser)

    fun navigateToLogin()
}