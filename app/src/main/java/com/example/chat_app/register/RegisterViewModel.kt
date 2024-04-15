package com.example.chat_app.register

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chat_app.FirebaseUtils
import com.example.chat_app.model.AppUser
import com.example.chat_app.model.DataUtils
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class RegisterViewModel : ViewModel() {
    val firstnameState = mutableStateOf("")
    val firstnameErrorState = mutableStateOf<String?>(null)
    val emailState = mutableStateOf("")
    val emailErrorState = mutableStateOf<String?>(null)
    val passwordState = mutableStateOf("")
    val passwordErrorState = mutableStateOf<String?>(null)
    val auth = Firebase.auth
    val isLoading = mutableStateOf(false)
    val events = mutableStateOf<RegisterEvent>(RegisterEvent.Idle)





    fun register(){
        if(validateFields()){
            isLoading.value = true
            auth.createUserWithEmailAndPassword(emailState.value,passwordState.value)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful){
                        isLoading.value = false
                        Log.d("TAG Exception Register ","${task.exception?.message}")
                        return@addOnCompleteListener
                    }
                    val uid = task.result.user?.uid
                    // Add user to cloud Fire-store
                    addUserToFirestore(uid)

                }
        }
    }

    private fun addUserToFirestore(uid: String?){
        val user = AppUser(uid , firstnameState.value, emailState.value)
        FirebaseUtils.addUser(user, onSuccessListener = {
            isLoading.value = false
            DataUtils.appUser = user
            events.value = RegisterEvent.NavigateToHome(user)
        }, onFailureListener = {
            isLoading.value =false
            Log.d("TAG Exception addUserToFirestore: ","${it.message}" )
        })

    }

    private fun validateFields(): Boolean{
        if (firstnameState.value.isEmpty() && firstnameState.value.isBlank()){
            firstnameErrorState.value= "Required"
            return false
        }else{
            firstnameErrorState.value =null
        }

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