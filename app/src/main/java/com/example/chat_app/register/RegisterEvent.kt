package com.example.chat_app.register

import com.example.chat_app.model.AppUser

sealed interface RegisterEvent {
    data object Idle : RegisterEvent
    data class NavigateToHome(val user: AppUser) : RegisterEvent

}