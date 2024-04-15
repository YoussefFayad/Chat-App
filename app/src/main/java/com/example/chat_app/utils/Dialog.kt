package com.example.chat_app.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.chat_app.ui.theme.ProgressColor


@Composable
fun LoadingDialog(isLoading: MutableState<Boolean>){
    if (isLoading.value) {
        Dialog(onDismissRequest = { isLoading.value = false }) {
            CircularProgressIndicator(
                color = ProgressColor,
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .size(80.dp)
                    .padding(24.dp),
                strokeWidth = 6.dp,

            )
        }
    }
}