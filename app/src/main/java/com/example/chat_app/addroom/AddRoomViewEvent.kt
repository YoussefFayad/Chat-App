package com.example.chat_app.addroom

sealed interface AddRoomViewEvent {
    data object Idle : AddRoomViewEvent
    data object NavigateBack : AddRoomViewEvent
}