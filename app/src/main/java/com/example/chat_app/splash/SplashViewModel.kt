package com.example.chat_app.splash

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chat_app.FirebaseUtils
import com.example.chat_app.model.AppUser
import com.example.chat_app.model.DataUtils
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class SplashViewModel: ViewModel() {
    val event = mutableStateOf<SplashEvent>(SplashEvent.Idle)
    val auth = Firebase.auth

    fun navigate() {
        if (auth.currentUser != null) {
            getUserFromFirestore(auth.currentUser?.uid ?: run {
                navigateToLogin()
                return
            })
        } else {
            navigateToLogin()
        }
    }

    fun getUserFromFirestore(uid: String) {
        FirebaseUtils.getUser(
            uid,
            onSuccessListener = { docSnapshot ->
                val user = docSnapshot.toObject(AppUser::class.java)
                DataUtils.appUser = user
                navigateToHome(user!!)
        }, onFailureListener = {
                Log.e("TAG", "getUserFromFire-store: ${it.message}")
                navigateToLogin()
        })
    }

    private fun navigateToLogin(){
        event.value = SplashEvent.NavigateToLogin
    }

   private fun navigateToHome(user: AppUser){
       event.value = SplashEvent.NavigateToHome(user)
   }

}