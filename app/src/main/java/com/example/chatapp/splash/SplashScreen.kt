package com.example.chatapp.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapp.R
import com.example.chatapp.home.HomeActivity
import com.example.chatapp.login.LoginActivity
import com.example.chatapp.model.AppUser
import com.example.chatapp.ui.theme.ChatAppTheme

class SplashScreen : ComponentActivity(),Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {

                Splash(navigator = this)





            }
        }
    }

    override fun navigateToHome(user: AppUser) {
        val intent = Intent(this@SplashScreen, HomeActivity::class.java)
        intent.putExtra("user",user)
        startActivity(intent)
        finish()
    }

    override fun navigateToLogin() {
        val intent = Intent(this@SplashScreen, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}

@Preview (showSystemUi = true, showBackground = true)
@Composable
fun Splash(splashViewModel: SplashViewModel = viewModel(),navigator: Navigator){

    LaunchedEffect(Unit){
        Handler(Looper.getMainLooper()).postDelayed({

            splashViewModel.splashNavigator = navigator

            splashViewModel.navigate()

        },1500)
    }



    Column (verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize(1f)
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Image(painter = painterResource(id = R.drawable.chat_app_logo),
            contentDescription = "app logo",
            modifier = Modifier.fillMaxWidth(0.5f),
            contentScale = ContentScale.Crop)

        Spacer(modifier = Modifier.weight(1f))

        Image(painter = painterResource(id = R.drawable.route_watermark),
            contentDescription = "route watermark",
            modifier = Modifier.fillMaxWidth(0.5f),
            contentScale = ContentScale.Crop)
    }




}

