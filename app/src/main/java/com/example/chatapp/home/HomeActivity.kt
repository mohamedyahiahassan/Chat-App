package com.example.chatapp.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.R
import com.example.chatapp.home.ui.theme.ChatAppTheme
import com.example.chatapp.ui.theme.cyan
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapp.addRoom.AddRoomActivity
import com.example.chatapp.chatRoom.ChatActivity
import com.example.chatapp.model.Room
import com.example.chatapp.utils.ChatTopBar

class HomeActivity : ComponentActivity(),HomeNavigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {

                HomeContent(homeNavigator = this)

            }
        }
    }

    override fun navigateToAddRoomActivity() {
        val intent = Intent(this@HomeActivity, AddRoomActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun navigateToChat(room: Room) {
        val intent = Intent(this@HomeActivity, ChatActivity::class.java)
        intent.putExtra("Room",room)
        Log.e("RoomID8",room.roomID.toString())
        startActivity(intent)
    }


}

@Composable
fun HomeContent(viewModel: HomeViewModel = viewModel(), homeNavigator: HomeNavigator){

   viewModel.navigator = homeNavigator
    Scaffold(
        topBar = { ChatTopBar(text1 = "Chat App")},

        floatingActionButton ={
            FloatingActionButton(
                shape = CircleShape,
                containerColor = cyan,
                modifier = Modifier.size(64.dp),
                onClick = {
                    viewModel.navigator?.navigateToAddRoomActivity()
                }) {

                Image(painter = painterResource(id = R.drawable.icon_add),
                    contentDescription ="ic_add",
                  )
            }
        }


    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.background),
                    contentScale = ContentScale.Crop
                )
        ) {
            it
            RoomList(viewModel,it)
        }


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomList(viewModel: HomeViewModel,paddingValues: PaddingValues){

    LaunchedEffect(key1 = Unit){
        viewModel.getRooms()
    }


    LazyVerticalGrid(columns = GridCells.Fixed(2),
        contentPadding = paddingValues, content ={
        items(viewModel.roomsList.size){

            Card (
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(Color.White),
                modifier = Modifier.padding(20.dp),
                onClick = {
                    viewModel.navigator?.navigateToChat(viewModel.roomsList.get(it))
                }
            ) {
               /* Image(painter = painterResource(id = viewModel.roomsList[it].roomImage ?:0),
                    contentDescription ="room_item",
                    modifier = Modifier
                        .size(85.dp)
                        .align(Alignment.CenterHorizontally))*/
                Text(text = viewModel.roomsList[it].roomName ?: "",
                    fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.size(20.dp))

            }
        }
    } )


}



