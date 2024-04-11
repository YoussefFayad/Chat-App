package com.example.chat_app.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chat_app.R
import com.example.chat_app.ui.theme.Blue

@Composable
fun ChatAuthButton(modifier: Modifier =Modifier,title :String,onClick: () -> Unit){
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


@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewButton(){
    ChatAuthButton(title = "Login") {
        
    }
}