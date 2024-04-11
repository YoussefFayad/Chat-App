package com.example.chat_app.splash

sealed interface SplashEvent {
    data object Idle : SplashEvent
    data object NavigateToLogin :SplashEvent
    data object NavigateToHome : SplashEvent

}