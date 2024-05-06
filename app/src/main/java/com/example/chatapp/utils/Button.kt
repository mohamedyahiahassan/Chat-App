package com.example.chatapp.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.R
import com.example.chatapp.ui.theme.cyan

@Composable
fun ChatAuthButton(title:String, onButtonClick: ()->Unit ){

    Button(shape = RoundedCornerShape(2.dp),
        colors = ButtonDefaults.buttonColors(cyan),
        modifier = Modifier.fillMaxWidth(),onClick = { onButtonClick() }) {

        Text(text = title, fontSize = 14.sp)

        Spacer(modifier = Modifier.weight(1f))

        Image(painter = painterResource(id = R.drawable.arrow_login),
            contentDescription = "login arrow",
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun SendButton(onButtonClick: () -> Unit){

    Button(
        onClick = {

                  onButtonClick()

        },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = cyan)
        ) {
        Text(
            text = "Send",
            color = Color.White,
                fontSize = 18.sp
            )

        Spacer(modifier = Modifier.width(4.dp))
        Icon(painter = painterResource(id = R.drawable.send), contentDescription = "Icon Send")
    }
}



