package com.example.chat_app.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chat_app.FirebaseUtils
import com.example.chat_app.model.AppUser
import com.example.chat_app.model.DataUtils
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginViewModel : ViewModel() {
    val emailState = mutableStateOf("")
    val emailErrorState = mutableStateOf<String?>(null)
    val passwordState = mutableStateOf("")
    val passwordErrorState = mutableStateOf<String?>(null)
    val auth = Firebase.auth
    val isLoading = mutableStateOf(false)
    val event = mutableStateOf<LoginEvent>(LoginEvent.Idle)

    fun navigateTORegister(){
        event.value =LoginEvent.NavigateToRegister
    }
    fun navigateToHome(user: AppUser){
        event.value = LoginEvent.NavigateToHome(user)
    }

    fun resetEvent(){
        event.value = LoginEvent.Idle
    }

    fun login(){
        if(validateFields()){
            isLoading.value = true
            auth.signInWithEmailAndPassword(emailState.value,passwordState.value)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful){
                        isLoading.value = false
                        Log.d("TAG Exception Login ","${task.exception?.message}")
                        return@addOnCompleteListener
                    }
                    val uid = task.result.user?.uid
                    getUserFromFireStore(uid!!)

                }

        }
    }


    private fun getUserFromFireStore(uid:String){
        FirebaseUtils.getUser(uid, onSuccessListener = { documentSnapshot ->
            isLoading.value = false
            val user = documentSnapshot.toObject(AppUser::class.java)
            DataUtils.appUser = user
            navigateToHome(user!!)
        }, onFailureListener = {
            isLoading.value = false
        })
    }

    private fun validateFields(): Boolean{

        if (emailState.value.isEmpty() && emailState.value.isBlank()){
            emailErrorState.value= "Required"
            return false
        }else{
            emailErrorState.value =null
        }

        if (passwordState.value.isEmpty() && passwordState.value.isBlank()){
            passwordErrorState.value= "Required"
            return false
        }else{
            passwordErrorState.value =null
        }
        if (passwordState.value.length <6){
            passwordErrorState.value = "Password can't be less that 6 Chars or numbers"
            return false
        }else{
            passwordErrorState.value =null
        }


        return true
    }
}

