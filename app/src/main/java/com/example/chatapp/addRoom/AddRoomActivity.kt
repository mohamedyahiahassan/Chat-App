package com.example.chatapp.addRoom

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.R
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.utils.ChatAuthTextField
import com.example.chatapp.utils.ChatTopBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapp.home.HomeActivity
import com.example.chatapp.ui.theme.cyan
import com.example.chatapp.utils.SelectRoomCategory

class AddRoomActivity : ComponentActivity(),Navigation {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                // A surface container using the 'background' color from the theme

                AddRoomContent(navigator = this)
            }
        }
    }

    override fun navigateToHome() {
       val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun navigateUp() {
        finish()
    }
}

@Composable
fun AddRoomContent(viewModel: AddRoomViewModel = viewModel(),navigator:Navigation){

    viewModel.addRoomNavigator = navigator

    Column(modifier = Modifier.fillMaxSize(1f)
    ) {


        Scaffold(
            topBar = { ChatTopBar(stringResource(R.string.add_room)){
                navigator.navigateUp()
            } }
        ) {
it

            Column(modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.background),
                    contentScale = ContentScale.Crop
                ),verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                RoomDetails(viewModel,it,navigator)

            }

        }

    }
}


@Composable
fun RoomDetails(viewModel: AddRoomViewModel,paddingValues: PaddingValues,navigator:Navigation) {


        Card(
            shape = RoundedCornerShape(
                topStart = 10.dp,
                topEnd = 10.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding() + 50.dp)
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.8f)
        ) {

            Text(
                text = stringResource(R.string.create_new_room),
                fontWeight = FontWeight.Bold,
                fontSize = 21.sp,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Image(
                painter = painterResource(id = R.drawable.create_new_room_img),
                contentDescription = "create_new_room_image",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(150.dp)
            )

            Column (horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()){

                ChatAuthTextField(
                    label = stringResource(R.string.enter_room_name),
                    placeHolder = stringResource(R.string.enter_room_name),
                    state = viewModel.roomNameState,
                    errorState = viewModel.roomNameErrorState.value,
                    modifierIn = Modifier.fillMaxWidth(0.8F)
                )

                Spacer(modifier = Modifier.fillMaxHeight(0.1F))

                SelectRoomCategory(viewModel())
                Spacer(modifier = Modifier.fillMaxHeight(0.05F))

                ChatAuthTextField(
                    label = "Enter Room Description",
                    placeHolder = "Enter Room Description",
                    state = viewModel.roomDescState,
                    errorState = viewModel.roomDescErrorState.value,
                    modifierIn = Modifier.fillMaxWidth(0.8F)

                )
                Spacer(modifier = Modifier.fillMaxHeight(0.2F))
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = cyan),
                    onClick = {

                             viewModel.addRoom()
                    },

                    modifier = Modifier.fillMaxWidth(0.7F)
                    ) {
                    Text(text = stringResource(R.string.create), fontSize = 18.sp)
                    
                }
            }
        }

}


