package com.example.chat_app.utils

import android.text.Layout.Alignment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chat_app.R
import com.example.chat_app.ui.theme.Blue
import com.example.chat_app.ui.theme.Gray
import com.example.chat_app.ui.theme.Gray2

@Composable
fun ChatAuthTextField(
    state : MutableState<String>,
    error: String?,
    label : String,
    modifier: Modifier = Modifier,
    isPassword: Boolean =false
){
    Column(modifier = modifier.fillMaxWidth(0.9F)) {

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.value,
            onValueChange = {
                state.value = it
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                focusedIndicatorColor = Blue,
                unfocusedIndicatorColor = Gray,
                errorIndicatorColor = Color.Red

            ),
            keyboardOptions = if (isPassword)KeyboardOptions(keyboardType = KeyboardType.Password) else KeyboardOptions.Default,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            label ={
                Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Normal )
            }

        )
        if (error!= null){
            Text(
                text = error,
                color = Color.Red,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(start = 18.dp)
                    .align(androidx.compose.ui.Alignment.Start)
            )
        }
    }
}

@Composable
fun ChatInputTextField(modifier: Modifier = Modifier, state: MutableState<String>) {
    OutlinedTextField(
        value = state.value,
        onValueChange = {
            state.value = it
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Gray2,
            focusedIndicatorColor = Gray2
        ),
        shape = RoundedCornerShape(topEnd = 20.dp),
        placeholder = {
            Text(text = stringResource(R.string.type_a_message))
        }

    )
}