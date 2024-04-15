package com.example.chat_app.login

import com.example.chat_app.model.AppUser

sealed interface LoginEvent {

    data object Idle :LoginEvent
    data object NavigateToRegister : LoginEvent
    data class NavigateToHome(val user: AppUser) : LoginEvent
}