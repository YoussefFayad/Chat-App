package com.example.chat_app.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chat_app.R
import com.example.chat_app.home.HomeActivity
import com.example.chat_app.utils.ChatAuthRegisterButton
import com.example.chat_app.utils.ChatAuthTextField
import com.example.chat_app.utils.ChatToolBar
import com.example.chat_app.utils.LoadingDialog


class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegisterContent(
                onRegistrationSuccess = {
                    finishAffinity()
                }){
                    finish()

            }
        }
    }
}

@Composable
fun RegisterContent(
    viewModel: RegisterViewModel = viewModel(),
    onRegistrationSuccess: () -> Unit,
    onFinish : () -> Unit
) {
    Scaffold(topBar = {
        ChatToolBar(title = stringResource(R.string.create_account)){
            onFinish()
        }
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
            ChatAuthTextField(
                state = viewModel.firstnameState,
                error = viewModel.firstnameErrorState.value,
                label = stringResource(R.string.first_name),

            )

            Spacer(modifier = Modifier.height(8.dp))
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

            Spacer(modifier = Modifier.fillMaxHeight(0.1F))


            ChatAuthRegisterButton(title = stringResource(id = R.string.create_account)) {
                viewModel.register()
            }

        }
        TriggerEvents(event = viewModel.events.value) {
            onRegistrationSuccess()
        }
        LoadingDialog(isLoading = viewModel.isLoading)
    }
}

@Composable
fun TriggerEvents(
    event: RegisterEvent,
    viewModel: RegisterViewModel = viewModel(),
    onRegistrationSuccess: () -> Unit
    ){
    when(event){
        RegisterEvent.Idle -> {}

        is RegisterEvent.NavigateToHome -> {
            val context = LocalContext.current
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
            onRegistrationSuccess()
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterContentPreview(){
    RegisterContent(onRegistrationSuccess = {}){

    }
}