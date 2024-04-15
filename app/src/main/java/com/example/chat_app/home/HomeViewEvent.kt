package com.example.chat_app.home

import com.example.chat_app.model.Room

sealed interface HomeViewEvent {
    data object Idle : HomeViewEvent
    data object NavigateToAddRoomDestination : HomeViewEvent

    data class NavigateToChatScreen(val room: Room) : HomeViewEvent


}