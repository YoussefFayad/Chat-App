package com.example.chat_app.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chat_app.FirebaseUtils
import com.example.chat_app.model.Room

class HomeViewModel : ViewModel() {
    val events = mutableStateOf<HomeViewEvent>(HomeViewEvent.Idle)
    val isLoading = mutableStateOf(false)
    val roomsList = mutableStateListOf<Room>()

    fun getRoomsFromFirestore() {
        isLoading.value = true
        FirebaseUtils.getRooms(onSuccessListener = {
            isLoading.value = false
            roomsList.clear()
            val list = it.toObjects(Room::class.java)
            roomsList.addAll(list)
        }, onFailureListener = {
            isLoading.value = false
            Log.e("TAG", "getRoomsFromFirestore: ${it.message}")
        }
        )
    }


    fun navigateToChatScreen(room: Room) {
        events.value = HomeViewEvent.NavigateToChatScreen(room)
    }
    fun navigateTOAddRoomScreen(){
        events.value = HomeViewEvent.NavigateToAddRoomDestination
    }

    fun resetToIdleState(){
        events.value = HomeViewEvent.Idle

    }
}