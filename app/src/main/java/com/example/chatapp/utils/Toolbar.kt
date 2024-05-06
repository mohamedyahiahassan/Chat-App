package com.example.chatapp.utils

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar(text1: String,onNavigationClick:(()->Unit)? = null){
    
    CenterAlignedTopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,
        titleContentColor = Color.White,

    ), navigationIcon = {
            if(onNavigationClick!=null){
                Image(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription ="back arrow",
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .clickable { onNavigationClick() }
                )

        }
    },
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Image(painter = painterResource(id = R.drawable.more_icon),
                    contentDescription = "more")

            }},

            title = {
            Text(text = text1, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        },
        modifier = Modifier.fillMaxWidth(1f))



}

@Preview
@Composable
fun ChatTopBarPreview(){
    
    ChatTopBar(stringResource(R.string.login),)
    
}