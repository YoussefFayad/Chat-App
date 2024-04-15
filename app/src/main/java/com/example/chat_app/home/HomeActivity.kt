package com.example.chat_app.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chat_app.R
import com.example.chat_app.utils.ChatToolBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chat_app.Constants
import com.example.chat_app.addroom.AddRoomActivity
import com.example.chat_app.chat.ChatActivity
import com.example.chat_app.model.Category
import com.example.chat_app.model.Room
import com.example.chat_app.ui.theme.Blue

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeContent()
        }
    }
}

@Composable
private fun HomeContent(
    viewModel: HomeViewModel = viewModel(),
){
    Scaffold(
        topBar = {
            ChatToolBar(title = stringResource(R.string.chat_app))
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                    viewModel.navigateTOAddRoomScreen()
            },
                containerColor = Blue,
                contentColor = Color.White,
                shape = CircleShape
             ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_add),
                    contentDescription = stringResource(R.string.icon_add_room),
                    modifier = Modifier.size(30.dp),

                )
            }
        }
    ) {
        it
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.background),
                    contentScale = ContentScale.FillBounds
                )
                .padding(top = it.calculateTopPadding())
                .padding(top = 180.dp)
        ) {
            RoomsLazyGrid()
        }
    }
    TriggerEvent(event = viewModel.events.value)
}

@Composable
fun TriggerEvent(
    event: HomeViewEvent,
    viewModel: HomeViewModel = viewModel(),
){
    val context = LocalContext.current
    when(event){
        HomeViewEvent.Idle -> {}

        HomeViewEvent.NavigateToAddRoomDestination -> {
            val intent = Intent(context, AddRoomActivity::class.java )
            context.startActivity(intent)
            viewModel.resetToIdleState()
        }

        is HomeViewEvent.NavigateToChatScreen -> {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(Constants.ROOM_KEY, event.room)
            context.startActivity(intent)
            viewModel.resetToIdleState()
        }
    }
}
@Composable
fun RoomsLazyGrid(viewModel: HomeViewModel = viewModel()) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getRoomsFromFirestore()
    }
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(viewModel.roomsList.size) { position ->

            RoomCard(room = viewModel.roomsList[position])
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomCard(room: Room, viewModel: HomeViewModel = viewModel()) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        onClick = {
            viewModel.navigateToChatScreen(room)
        }
    ) {
        Image(
            painter = painterResource(
                id = Category.fromId(room.categoryId ?: Category.MOVIES).image ?: R.drawable.movies
            ), contentDescription = stringResource(R.string.room_category_image),
            modifier = Modifier
                .size(86.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop
        )
        Text(
            text = room.name ?: "", fontSize = 14.sp, fontWeight = FontWeight.Medium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun RoomPreview() {
    RoomCard(room = Room("Android Room", Category.MOVIES))
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun HomeScreenPreview(){
    HomeContent()
}