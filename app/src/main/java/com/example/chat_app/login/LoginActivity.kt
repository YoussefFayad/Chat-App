package com.example.chat_app.login

import android.app.PendingIntent.OnFinished
import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
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
import com.example.chat_app.home.HomeActivity
import com.example.chat_app.register.RegisterActivity
import com.example.chat_app.ui.theme.Black2
import com.example.chat_app.utils.ChatAuthLoginButton
import com.example.chat_app.utils.ChatAuthTextField
import com.example.chat_app.utils.LoadingDialog

class LoginActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginContent{
                finish()
            }
        }
    }
}

@Composable
private fun LoginContent(viewModel: LoginViewModel = viewModel(),onFinish: () -> Unit){
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
                label = stringResource(R.string.password),
                isPassword = true
            )
            Spacer(modifier = Modifier.height(5.dp))
            TextButton(onClick = { /*TODO*/ }, modifier = Modifier
                .padding(start = 10.dp)
                .align(Alignment.Start)) {
                Text(
                    text = stringResource(R.string.forgot_password),
                    color = Black2,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
            }

            Spacer(modifier = Modifier.height(5.dp))
            ChatAuthLoginButton(title = stringResource(id = R.string.login), onClick ={
                viewModel.login()
            })
            Spacer(modifier = Modifier.height(5.dp))
            TextButton(
                onClick = {
                viewModel.navigateTORegister()
            }, modifier = Modifier
                    .padding(start = 10.dp)
                    .align(Alignment.Start)) {
                Text(
                    text = stringResource(R.string.or_create_my_account),
                    color = Black2,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
            }


        }
    }
    TriggerEvents(event = viewModel.event.value){
        onFinish()
    }
    LoadingDialog(isLoading = viewModel.isLoading)
}


@Composable
fun TriggerEvents(
    event: LoginEvent,
    viewModel: LoginViewModel = viewModel(),
    onFinish :() -> Unit
){
    val context = LocalContext.current
    when(event){
        LoginEvent.Idle -> {}

        is LoginEvent.NavigateToHome -> {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
            onFinish()

        }
        LoginEvent.NavigateToRegister -> {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
            viewModel.resetEvent()
        }

    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun LoginScreenPreview(){
    LoginContent{}
}