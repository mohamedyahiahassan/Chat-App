package com.example.chatapp.chatRoom

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.chatRoom.ui.theme.ChatAppTheme
import com.example.chatapp.model.Room
import com.example.chatapp.utils.ChatTopBar
import com.example.chatapp.R
import com.example.chatapp.utils.ChatMessagingTextField
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapp.model.Message
import com.example.chatapp.model.User
import com.example.chatapp.ui.theme.cyan
import com.example.chatapp.utils.SendButton
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale

class ChatActivity : ComponentActivity() {

    var room: Room? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
             room = intent.getParcelableExtra("Room",Room::class.java)
        } else{
            room = intent.getParcelableExtra<Room>("Room")
        }

        Log.e("RoomID",room?.roomID.toString())
        setContent {
            ChatAppTheme {
                // A surface container using the 'background' color from the theme

                ChatContent(room!!)
            }
        }
    }
}

@Composable
fun ChatContent(room:Room,viewModel: ChatActivityViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){

    LaunchedEffect(key1 = Unit){
        viewModel.room = room
    }



    Scaffold(
        topBar = {
            ChatTopBar(text1 = room.roomName ?: "",
                onNavigationClick = {

                })
        }

    ) {
        it
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)

                .paint(
                    painterResource(id = R.drawable.background),
                    contentScale = ContentScale.Crop
                )
        ) {

            ChatLazyColumn(viewModel)
            ChatMessagingBar(viewModel)

        }
    }
}

@Composable
fun ChatLazyColumn(viewModel: ChatActivityViewModel) {

    LaunchedEffect(key1 = Unit) {

        viewModel.room?.let { viewModel.listenToMessageChange(it) }
    }

    val lazyColumnListState = rememberLazyListState()
    val corroutineScope = rememberCoroutineScope()

    Card (
        shape = RoundedCornerShape(topEnd = 15.dp, topStart = 15.dp),
        colors = CardDefaults.cardColors(Color.White),
        modifier =  Modifier
            .padding(top = 50.dp, start = 20.dp, end = 20.dp)
            .fillMaxHeight(0.9F)

    ) {


        LazyColumn(
            state = lazyColumnListState
        ) {

            corroutineScope.launch {

                if (viewModel.messagesList.size!=0)
                    lazyColumnListState.scrollToItem(viewModel.messagesList.size-1)

            }
            items(viewModel.messagesList.size) {


                if (viewModel.messagesList[it].senderId == User.user?.uid) {
                    SentMessageCard(message = viewModel.messagesList[it])

                } else {
                    ReceivedMessageCard(message = viewModel.messagesList[it])

                }
            }
        }
    }
}

@Composable
fun ReceivedMessageCard(message: Message) {
    val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val date = Date(message.dateTime!!)
    val dateString = simpleDateFormat.format(date)
    Log.e("Hello", "${message.senderName}")
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = message.senderName ?: "", style = TextStyle(color = Color.Black))

        Row(
            horizontalArrangement = Arrangement.Start, modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {

            Text(
                text = message.content ?: "",
                modifier = Modifier
                    .background(
                        Color.Gray,
                        shape = RoundedCornerShape(
                            bottomEnd = 24.dp,
                            topEnd = 24.dp,
                            topStart = 24.dp
                        )
                    )
                    .padding(16.dp),
                style = TextStyle(fontSize = 22.sp, color = colorResource(id = R.color.black))
            )
            Text(
                text = dateString,
                modifier = Modifier.align(Alignment.Bottom),
                style = TextStyle(color = Color.Black)
            )

        }
    }
}

@Composable
fun SentMessageCard(message: Message) {
    val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val date = Date(message.dateTime!!)
    val dateString = simpleDateFormat.format(date)
    Row(
        horizontalArrangement = Arrangement.End, modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        Text(
            text = dateString,
            modifier = Modifier.align(Alignment.Bottom),
            style = TextStyle(color = Color.Black)
        )
        Text(
            text = message.content ?: "",
            modifier = Modifier
                .background(
                    cyan,
                    shape = RoundedCornerShape(
                        bottomStart = 24.dp,
                        topEnd = 24.dp,
                        topStart = 24.dp
                    )
                )
                .padding(16.dp),
            style = TextStyle(fontSize = 22.sp, color = colorResource(id = R.color.white))
        )

    }
}


@Composable
fun ChatMessagingBar(viewModel: ChatActivityViewModel) {

    Row (

        verticalAlignment = Alignment.CenterVertically,

        ){
        Spacer(modifier = Modifier.width(8.dp))
        ChatMessagingTextField(
            placeHolder = "Type your message" ,
            state = viewModel.messageTextState,
        )
        Spacer(modifier = Modifier.width(8.dp))
        SendButton(){

           viewModel.sendMessage()


        }
    }


}


