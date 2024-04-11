package com.example.chat_app.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chat_app.R
import com.example.chat_app.ui.theme.Black
import com.example.chat_app.utils.ChatToolBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chat_app.ui.theme.Black2
import com.example.chat_app.utils.ChatAuthButton
import com.example.chat_app.utils.ChatAuthTextField

class LoginActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginContent()
        }
    }
}

@Composable
private fun LoginContent(viewModel: LoginViewModel = viewModel()){
    Scaffold(topBar = {
        ChatToolBar(title = stringResource(R.string.login))
    }) {
        it
        Column(modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.4F))
            Text(
                text = stringResource(R.string.welcome_back),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Black,
                modifier = Modifier
                    .padding(start = 18.dp)
                    .align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(12.dp))
            ChatAuthTextField(
                state = viewModel.emailState,
                error = viewModel.emailErrorState.value,
                label = stringResource(R.string.email)
            )
            Spacer(modifier = Modifier.height(8.dp))
            ChatAuthTextField(
                state = viewModel.passwordState,
                error = viewModel.passwordErrorState.value,
                label = stringResource(R.string.password)
            )
            Spacer(modifier = Modifier.height(5.dp))
            TextButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(start = 10.dp).align(Alignment.Start)) {
                Text(
                    text = stringResource(R.string.forgot_password),
                    color = Black2,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
            }

            Spacer(modifier = Modifier.height(5.dp))
            ChatAuthButton(title = stringResource(id = R.string.login)) {}

            Spacer(modifier = Modifier.height(5.dp))
            TextButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(start = 10.dp).align(Alignment.Start)) {
                Text(
                    text = stringResource(R.string.or_create_my_account),
                    color = Black2,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
            }


        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewLoginScreen(){
    LoginContent()
}