package com.example.chat_app.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chat_app.R
import com.example.chat_app.ui.theme.ProgressColor
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chat_app.Constants
import com.example.chat_app.home.HomeActivity
import com.example.chat_app.login.LoginActivity


class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            SplashContent{
                finish()
            }


        }
    }
}

@Composable
fun SplashContent(viewModel: SplashViewModel= viewModel(), onFinish :() -> Unit) {

    LaunchedEffect(key1 = Unit) {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                viewModel.navigate()
            },1000
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1F))

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App logo",
            modifier = Modifier.fillMaxHeight(0.2F),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(20.dp))

        CircularProgressIndicator(color = ProgressColor)

        Spacer(modifier = Modifier.weight(1F))

        Image(
            painter = painterResource(id = R.drawable.signature),
            contentDescription = "App Development signature",
            modifier = Modifier.fillMaxHeight(0.2F),
            contentScale = ContentScale.Crop
        )
    }
    TriggerEvents(event = viewModel.event.value){
        onFinish()
    }
}


@Composable
fun TriggerEvents(
    event: SplashEvent,
    viewModel: SplashViewModel = viewModel(),
    onFinish: () -> Unit

    ){
    val context = LocalContext.current
    when(event){
        SplashEvent.Idle -> {}

        is SplashEvent.NavigateToHome -> {
            val intent = Intent(context,HomeActivity ::class.java)
            //Parcelable
            intent.putExtra(Constants.USER_Key,event.user)
            context.startActivity(intent)
            onFinish()
        }
        SplashEvent.NavigateToLogin -> {
            val intent = Intent(context,LoginActivity::class.java)
            context.startActivity(intent)
            onFinish()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashContentPreview() {
    SplashContent{}
}
