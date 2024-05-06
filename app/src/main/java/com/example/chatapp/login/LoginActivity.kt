package com.example.chatapp.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.R
import com.example.chatapp.home.HomeActivity
import com.example.chatapp.register.RegisterActivity
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.utils.ChatAuthButton
import com.example.chatapp.utils.ChatAuthTextField
import com.example.chatapp.utils.ChatTopBar
import com.example.chatapp.utils.LoadingDialog

class LoginActivity : ComponentActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                // A surface container using the 'background' color from the theme
                LoginScreenContent(navigator = this)
            }
        }
    }

    override fun navigateToRegistration() {

        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToHome() {
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(intent)
    }


}



@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginScreenContent(viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(), navigator: Navigator){

    Column(modifier = Modifier.fillMaxSize(1f)
        ) {


    Scaffold(
        topBar = { ChatTopBar(stringResource(R.string.login)) }
    ) {
        it

        Column(modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop
            ),verticalArrangement = Arrangement.Center
            ) {

            Spacer(modifier = Modifier.fillMaxHeight(0.4f))
            LoginDetails(viewModel,navigator)

        }

    }

    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginScreenContentPreview(){

}


@Composable
fun LoginDetails(viewModel: LoginViewModel, navigator: Navigator){


    viewModel.onCreateAccountClick = navigator

    var context = LocalContext.current

    Column(modifier = Modifier
        .padding(start = 20.dp, end = 20.dp)
        .fillMaxSize(1f)
    ) {

        Text(text = stringResource(R.string.welcome_back), fontSize = 24.sp, fontWeight = FontWeight.Bold)

        ChatAuthTextField(label = "Email", placeHolder = "Enter your email", state = viewModel.emailState,errorState = viewModel.errorState.value,false,true)

        ChatAuthTextField(label = "Password", placeHolder = "Create a new password",state = viewModel.passwordState,errorState = viewModel.passwordStateError.value,true)

        TextButton(onClick = { /*TODO*/ },
           ) {

            Text(text = "Forget Password?")

        }

        ChatAuthButton(title = "login", onButtonClick = {

            viewModel.userLogin(context)
        })

        TextButton(onClick = { viewModel.onCreateAccountClick?.navigateToRegistration()



                             },
        ) {

            Text(text = "Or Create My Account")

        }
        
        LoadingDialog(isLoading = viewModel.isLoading)


    }


}



