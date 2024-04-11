package com.example.chat_app.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SplashViewModel: ViewModel() {
    val event = mutableStateOf<SplashEvent>(SplashEvent.Idle)


    fun navigate(){
        navigateToLogin()
    }

    private fun navigateToLogin(){
        event.value = SplashEvent.NavigateToLogin
    }

   private fun navigateToHome(){
       event.value = SplashEvent.NavigateToHome
   }

}