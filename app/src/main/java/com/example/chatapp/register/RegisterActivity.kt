package com.example.chatapp.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatapp.R
import com.example.chatapp.login.LoginDetails
import com.example.chatapp.register.ui.theme.ChatAppTheme
import com.example.chatapp.utils.ChatAuthTextField
import com.example.chatapp.utils.ChatTopBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapp.home.HomeActivity
import com.example.chatapp.utils.ChatAuthButton

class RegisterActivity : ComponentActivity(),Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            ChatAppTheme {

                RegisterScreenContent(navigate = this)
            }
        }
    }


    override fun navigateToHome() {

        val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
    }


@Composable
fun RegisterScreenContent(viewModel : RegisterViewModel = viewModel(),navigate: Navigator) {


    Scaffold(
        topBar = {
            ChatTopBar(stringResource(R.string.create_account),{
               onBackPressed()
            })
        }
    ) {
        it

        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.background),
                    contentScale = ContentScale.Crop
                ), verticalArrangement = Arrangement.Center
        ) {



            RegisterDetails(viewModel,navigate)
        }

    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {

}

@Composable
fun RegisterDetails(viewModel: RegisterViewModel, navigate: Navigator) {

    viewModel.navigateToHomeClick = navigate

    Column(modifier = Modifier.padding(20.dp)) {


        ChatAuthTextField(
            "First Name",
            "Enter your name",
            viewModel.nameState,
            viewModel.nameErrorState.value,
            false
        )
        ChatAuthTextField(
            "Email Address",
            "Enter your email",
            viewModel.emailState,
            viewModel.emailErrorState.value,
            false,
            true
        )
        ChatAuthTextField(
            "Password",
            "Enter a new Password",
            viewModel.passwordState,
            viewModel.passErrorState.value,
            true
        )

        ChatAuthButton(title = "Create an account",{
            viewModel.authenticateUser(navigate)

        })
    }
}


}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview1() {
}