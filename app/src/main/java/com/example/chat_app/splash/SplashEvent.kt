package com.example.chat_app.splash

import com.example.chat_app.model.AppUser

sealed interface SplashEvent {
    data object Idle : SplashEvent
    data object NavigateToLogin :SplashEvent
    data class NavigateToHome(val user: AppUser) : SplashEvent

}