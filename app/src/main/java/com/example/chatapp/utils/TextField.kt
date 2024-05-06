package com.example.chatapp.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.R


@Composable
fun ChatAuthTextField(label:String,placeHolder:String,state: MutableState<String>,errorState: String?,
                      isPassword:Boolean = false,isEmail:Boolean = false,modifierIn: Modifier? = null) {

  //  var text by remember { mutableStateOf(TextFieldValue("")) }

    TextField(
        value = state.value,

        onValueChange = {newText->
            state.value = newText
        },
        label = { Text(text = label) },
        placeholder = { Text(text = placeHolder) },
        colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
        ), modifier = if (modifierIn==null)Modifier.fillMaxWidth()else modifierIn,


        keyboardOptions = if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Password)
        else if (isEmail) KeyboardOptions(keyboardType = KeyboardType.Email) else KeyboardOptions(),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None

    )

    if (!errorState.isNullOrEmpty()){
        Text(text = errorState, color = Color.Red, fontSize = 20.sp)
    }


}


@Composable
fun ChatMessagingTextField(placeHolder:String,
                           state: MutableState<String>) {
    
    OutlinedTextField(
        value = state.value,
        onValueChange = {
                        state.value =it
        },
        placeholder = {
            Text(text = placeHolder)
        },
        shape = RoundedCornerShape(topEnd = 12.dp, topStart = 0.dp, bottomEnd = 0.dp, bottomStart = 0.dp)
    )
    

}

