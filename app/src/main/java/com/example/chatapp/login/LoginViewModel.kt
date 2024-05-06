package com.example.chatapp.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.chatapp.model.AppUser
import com.example.chatapp.model.FireBaseUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginViewModel : ViewModel() {


    val emailState = mutableStateOf<String>("")
    val errorState = mutableStateOf<String?>(null)
    val passwordState = mutableStateOf<String>("")
    val passwordStateError = mutableStateOf<String?>(null)

    var onCreateAccountClick : Navigator? = null

    var auth: FirebaseAuth = Firebase.auth

    var isLoading = mutableStateOf<Boolean>(false)


    fun validateFields(): Boolean{

        if (emailState.value.isBlank()){

            errorState.value = "Invalid email"
            return false
        } else {

            errorState.value = null
        }

        if (passwordState.value.isBlank()){

            passwordStateError.value = "Invalid password"
            return false
        }else{

            passwordStateError.value = null
        }

        if (passwordState.value.length == 6){

            passwordStateError.value = "Password is too short"
            return false
        }else{

            passwordStateError.value = null
        }

        return true
    }

    fun userLogin(context: Context){

        if (!validateFields()) return

        isLoading.value = true

        auth.signInWithEmailAndPassword(emailState.value, passwordState.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.e("TAG", "signInWithEmail:success")
                    val user = auth.currentUser

                    isLoading.value = false

                    FireBaseUtils.getUser(task.result?.user?.uid!!,
                        onSuccessListener = {
                            Log.e("TAG", "3mltaha success")
                            val user = it.toObject(AppUser::class.java)
                            com.example.chatapp.model.User.user = user
                            isLoading.value = false
                            onCreateAccountClick?.navigateToHome()

                        }, onFailureListener = {
                            Log.e("Tag",it.message.toString())

                        })


                } else {
                    // If sign in fails, display a message to the user.
                    Log.e("TAG", "signInWithEmail:failure", task.exception)
                    isLoading.value = false

                    Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()

                }
            }
    }
}


interface Navigator {

    fun navigateToRegistration()

    fun navigateToHome()
}
