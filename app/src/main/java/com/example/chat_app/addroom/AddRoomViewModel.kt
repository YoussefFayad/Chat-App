package com.example.chat_app.addroom

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chat_app.FirebaseUtils
import com.example.chat_app.model.Category
import com.example.chat_app.model.Room



class AddRoomViewModel : ViewModel() {
    val roomNameState = mutableStateOf("")
    val roomNameErrorState = mutableStateOf<String?>(null)
    val roomDescriptionState = mutableStateOf("")
    val roomDescriptionErrorState = mutableStateOf<String?>(null)
    val isCategoryExpanded = mutableStateOf(false)

    val categoryList = Category.getCategoryList()
    val selectedCategoryItem = mutableStateOf(categoryList[0])
    val isLoading = mutableStateOf(false)
    val events = mutableStateOf<AddRoomViewEvent>(AddRoomViewEvent.Idle)
    fun addRoom() {
        if (validateFields()) {
            isLoading.value = true
            val room = Room(
                name = roomNameState.value, description = roomDescriptionState.value,
                categoryId = selectedCategoryItem.value.id
            )
            FirebaseUtils.addRoom(room, onSuccessListener = {
                isLoading.value = false
                events.value = AddRoomViewEvent.NavigateBack
            }, onFailureListener = {
                isLoading.value = false
                Log.e("TAG", "addRoom: error ${it.message}")
            })
        }

    }

    fun validateFields(): Boolean {
        if (roomNameState.value.isEmpty() || roomNameState.value.isBlank()) {
            roomNameErrorState.value = "Required"
            return false
        } else
            roomNameErrorState.value = null
        if (roomDescriptionState.value.isEmpty() || roomDescriptionState.value.isBlank()) {
            roomDescriptionErrorState.value = "Required"
            return false
        } else
            roomDescriptionErrorState.value = null
        return true
    }
}
