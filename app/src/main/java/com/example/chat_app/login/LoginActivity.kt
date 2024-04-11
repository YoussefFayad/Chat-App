package com.example.chat_app.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class LoginActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Login()

        }
    }
}

@Composable
private fun Login(){
    Text(text = "Login screen")
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewLoginScreen(){
    Login()
}