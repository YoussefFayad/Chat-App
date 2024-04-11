package com.example.chat_app.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Home()
        }
    }
}

@Composable
private fun Home(){
    Text(text = "Home screen")
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewHomeScreen(){
    Home()
}