package com.example.chat_app.addroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chat_app.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chat_app.ui.theme.Black3
import com.example.chat_app.ui.theme.Gray2
import com.example.chat_app.utils.ChatAuthTextField
import com.example.chat_app.utils.ChatToolBar
import com.example.chat_app.utils.CreateButton
import com.example.chat_app.utils.LoadingDialog


class AddRoomActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddRoomContent {
                    finish()
            }

        }
    }
}

@Composable
fun AddRoomContent(viewModel: AddRoomViewModel = viewModel(), onFinish: () -> Unit) {
    Scaffold(topBar = {
        ChatToolBar(title = stringResource(id = R.string.chat_app)) {
            onFinish()
        }
    }) { paddingValues ->
        paddingValues
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.background),
                    contentScale = ContentScale.FillBounds
                )
        ) {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .padding(top = paddingValues.calculateTopPadding())
                    .padding(top = 36.dp)
                    .fillMaxWidth(0.85F)
                    .align(Alignment.CenterHorizontally)

            ) {
                Text(
                    text = stringResource(R.string.create_new_room),
                    color = Black3,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_add_room_image),
                    contentDescription = stringResource(R.string.group_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight(0.12F)
                        .align(Alignment.CenterHorizontally)
                )
                ChatAuthTextField(
                    state = viewModel.roomNameState,
                    error = viewModel.roomNameErrorState.value,
                    label = stringResource(R.string.room_name),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(8.dp))
                CategoryDropDownMenu(Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.height(8.dp))
                ChatAuthTextField(
                    state = viewModel.roomDescriptionState,
                    error = viewModel.roomDescriptionErrorState.value,
                    label = stringResource(R.string.room_desc),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(20.dp))
                CreateButton(
                    onClick = {
                        viewModel.addRoom()
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9F)
                        .align(Alignment.CenterHorizontally)
                )
            }

        }
    }
    TriggerEvents(event = viewModel.events.value) {
        onFinish()
    }
    LoadingDialog(isLoading = viewModel.isLoading)
}

@Composable
fun TriggerEvents(event: AddRoomViewEvent, onFinish: () -> Unit) {
    when (event) {
        AddRoomViewEvent.Idle -> {}
        AddRoomViewEvent.NavigateBack -> {
            onFinish()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropDownMenu(modifier: Modifier = Modifier, viewModel: AddRoomViewModel = viewModel()) {
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = viewModel.isCategoryExpanded.value,
        onExpandedChange = {
            viewModel.isCategoryExpanded.value = !viewModel.isCategoryExpanded.value
        }) {
        TextField(
            value = viewModel.selectedCategoryItem.value.name ?: "",
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .menuAnchor()
                .border(1.dp, Gray2, RectangleShape),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.isCategoryExpanded.value)
            },
            leadingIcon = {
                Image(
                    painter = painterResource(
                        id = viewModel.selectedCategoryItem.value.image ?: R.drawable.sports
                    ),
                    modifier = Modifier.size(36.dp), contentScale = ContentScale.Crop,
                    contentDescription = stringResource(R.string.category_selection_icon)
                )
            }
        )
        ExposedDropdownMenu(
            expanded = viewModel.isCategoryExpanded.value,
            onDismissRequest = { viewModel.isCategoryExpanded.value = false }) {
            viewModel.categoryList.forEach { category ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(
                                    id = category.image ?: R.drawable.sports
                                ),
                                contentDescription = stringResource(R.string.category_image_selection),
                                modifier = Modifier.size(36.dp), contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = category.name ?: "", fontSize = 18.sp)
                        }


                    },
                    onClick = {
                        viewModel.selectedCategoryItem.value = category
                        viewModel.isCategoryExpanded.value = false
                    })
            }
        }
    }
}

@Preview
@Composable
private fun AddRoomPreview() {
    AddRoomContent {}
}


