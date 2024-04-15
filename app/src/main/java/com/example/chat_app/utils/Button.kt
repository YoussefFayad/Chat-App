package com.example.chat_app.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chat_app.R
import com.example.chat_app.ui.theme.Blue
import com.example.chat_app.ui.theme.LightGray
import com.example.chat_app.ui.theme.cyan

@Composable
fun ChatAuthLoginButton(modifier: Modifier =Modifier, title:String, onClick: () -> Unit){
    Button(
        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
        onClick = {
            onClick()

    }, shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Blue)
    ) {
            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.weight(0.9F))
            Image(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = stringResource(R.string.icon_arrow_right),
                modifier = Modifier.size(12.dp)
            )
    }
}

@Composable
fun ChatAuthRegisterButton(modifier: Modifier =Modifier, title:String, onClick: () -> Unit){
    Button(
        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
        onClick = {
            onClick()

        }, shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = LightGray)
    ) {
        Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray )
        Spacer(modifier = Modifier.weight(0.9F))
        Image(
            painter = painterResource(id = R.drawable.arrow_right),
            contentDescription = stringResource(R.string.icon_arrow_right),
            modifier = Modifier.size(12.dp)
        )
    }
}

@Composable
fun CreateButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = Blue, contentColor = Color.White)
    ) {
        Text(
            text = stringResource(R.string.create),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
        )
    }
}

@Composable
fun SendButton(onClickListener: () -> Unit) {
    Button(
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = cyan, contentColor = Color.White),
        onClick = { onClickListener() }) {
        Text(text = stringResource(R.string.send))
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_send),
            contentDescription = stringResource(R.string.icon_send_a_message)
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewButton(){
    //ChatAuthLoginButton(title = "Login") {}

    //ChatAuthRegisterButton(title = "Create Account") {}

    SendButton {}
}