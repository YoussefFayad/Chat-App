package com.example.chat_app.chat

import android.os.Build
import android.os.Bundle
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chat_app.Constants
import com.example.chat_app.R
import com.example.chat_app.model.DataUtils
import com.example.chat_app.model.Message
import com.example.chat_app.model.Room
import com.example.chat_app.ui.theme.Gray
import com.example.chat_app.utils.ChatToolBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chat_app.ui.theme.cyan
import com.example.chat_app.utils.ChatInputTextField
import com.example.chat_app.utils.SendButton


class ChatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                val room = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra(Constants.ROOM_KEY, Room::class.java)
                } else {
                    intent.getParcelableExtra(Constants.ROOM_KEY) as Room?
                }
                ChatContent(room!!) {
                    finish()
                }

        }
    }
}

@Composable
fun ChatContent(
    room: Room,
    viewModel: ChatViewModel = viewModel(),
    onFinish: () -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.room = room
    }
    Scaffold(topBar = {
        ChatToolBar(title = room.name ?: "") {
            onFinish()
        }
    }) {
        it
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.background),
                    contentScale = ContentScale.FillBounds
                )
                .padding(top = it.calculateTopPadding()),
            verticalArrangement = Arrangement.Bottom
        ) {
            MessagesLazyColumn()
            ChatInputBottomBar()
        }


    }
}

@Composable
fun MessagesLazyColumn(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = viewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.listenForMessages()
    }
    LazyColumn(
        modifier
            .fillMaxHeight(0.90F)
            .padding(36.dp)
            .background(Color.White, RoundedCornerShape(12.dp)),
        reverseLayout = true
    ) {
        items(viewModel.messagesListState.size) { position ->
            if (DataUtils.appUser?.uid == viewModel.messagesListState[position].senderId) {
                SentMessageCard(message = viewModel.messagesListState[position])
            } else {
                ReceivedMessageCard(message = viewModel.messagesListState[position])
            }
        }
    }
}

@Composable
fun SentMessageCard(message: Message) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Text(text = message.formatDateTime(), modifier = Modifier.align(Alignment.Bottom))
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            message.content ?: "",
            modifier = Modifier
                .background(
                    cyan,
                    RoundedCornerShape(bottomStart = 24.dp, topEnd = 24.dp, topStart = 24.dp)
                )
                .padding(8.dp)
                .padding(end = 8.dp),
            color = Color.White
        )


    }
}

@Composable
fun ReceivedMessageCard(message: Message) {
    Column {
        Text(text = message.senderName ?: "", modifier = Modifier.padding(start = 8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {


            Text(
                message.content ?: "",
                modifier = Modifier
                    .padding(4.dp)
                    .background(
                        Gray,
                        RoundedCornerShape(bottomEnd = 24.dp, topEnd = 24.dp, topStart = 24.dp)
                    )
                    .padding(8.dp)
                    .padding(end = 8.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = message.formatDateTime(), modifier = Modifier.align(Alignment.Bottom))


        }
    }
}

@Composable
fun ChatInputBottomBar(viewModel: ChatViewModel = viewModel()) {
    Row(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ChatInputTextField(state = viewModel.messageState)

        SendButton { viewModel.sendAMessage() }
    }
}

@Preview
@Composable
private fun ChatPreview() {
    ChatContent(Room("Android", id = "123")) {}
}

@Preview(showBackground = true)
@Composable
private fun SentMessagePreview() {
    SentMessageCard(Message("Hello", dateTime = 4231321))
}

@Preview(showBackground = true)
@Composable
private fun ReceivedMessagePreview() {
    ReceivedMessageCard(Message("Hello World", senderName = "Ahmed", dateTime = 4231321))
}