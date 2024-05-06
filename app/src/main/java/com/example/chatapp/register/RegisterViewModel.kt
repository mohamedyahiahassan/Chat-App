package com.example.chatapp.register

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.model.AppUser
import com.example.chatapp.model.FireBaseUtils
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.chatapp.register.Navigator


class RegisterViewModel : ViewModel() {

    var auth: FirebaseAuth =  Firebase.auth


    val nameState = mutableStateOf<String>("")
    val emailState = mutableStateOf<String>("")
    val passwordState = mutableStateOf<String>("")

    val nameErrorState = mutableStateOf<String?>(null)
    val emailErrorState = mutableStateOf<String?>(null)
    val passErrorState = mutableStateOf<String?>(null)

    var navigateToHomeClick : com.example.chatapp.register.Navigator? = null

    fun validateFields(): Boolean{

        if (nameState.value.isBlank()){

            nameErrorState.value = "Invalid email"
            return false
        } else {

            nameErrorState.value = null
        }

        if (emailState.value.isBlank()){

            emailErrorState.value = "Invalid email"
            return false
        } else {

            emailErrorState.value = null
        }

        if (passwordState.value.isBlank()){

            passErrorState.value = "Invalid password"
            return false
        }else{

            passErrorState.value = null
        }

        if (passwordState.value.length == 6){

            passErrorState.value = "Password is too short"
            return false
        }else{

            passErrorState.value = null
        }

        return true
    }

    fun authenticateUser(navigate:Navigator){

        if (!validateFields()) return

        auth.createUserWithEmailAndPassword(emailState.value,passwordState.value)
            .addOnCompleteListener {task ->

                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val uid = task.result.user?.uid

                  addUserToFireStore(uid!!,navigate)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.e("Tag", "createUserWithEmail:failure", task.exception)

                    return@addOnCompleteListener
                }


            }



    }

     fun addUserToFireStore(uid: String,navigate: com.example.chatapp.register.Navigator) {

         val user = AppUser(uid,nameState.value,emailState.value)

        FireBaseUtils.addUser(user,
            onSuccessListener = {
                com.example.chatapp.model.User.user = user
                navigate.navigateToHome()
            },
            onFailureListener = {})
    }

}

interface Navigator{

    fun navigateToHome()

}


