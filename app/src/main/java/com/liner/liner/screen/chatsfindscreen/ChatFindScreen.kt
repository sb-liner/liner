package com.liner.liner.screen.chatsfindscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.liner.liner.R
import com.liner.liner.data.model.User
import com.liner.liner.swipeAway

val TAG = "MainScreen"

@Composable
fun ChatFindScreen(
    viewModel: ChatFindViewModel = hiltViewModel()
) {

    when (viewModel.state.value) {
        is ChatFindScreenState.Loading -> LoadingScreen()
        is ChatFindScreenState.Loaded -> Row() {}
        is ChatFindScreenState.Connected -> ChatCard(User("Kim", emptyList(), 0, 25))
    }

}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LinearProgressIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatCard(user: User) {

    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .weight(1f)
                .swipeAway({}, {}, {}, {}, {}),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(all = 8.dp)
                            .height(70.dp)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            Row {
                                Image(
                                    painter = painterResource(R.drawable.dummy_image),
                                    contentDescription = "Match profile picture",
                                    modifier = Modifier
                                        .size(65.dp)
                                        .clip(CircleShape)
                                )

                                // Add a horizontal space between the image and the column
                                Spacer(modifier = Modifier.width(8.dp))

                                Column {
                                    Text(text = user.id)
                                    // Add a vertical space between the author and message texts
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = user.age.toString())
                                }
                            }
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                ElevatedButton(onClick = {}) {
                                    Icon(
                                        painterResource(id = R.drawable.baseline_bookmark_border_24),
                                        "save for later"
                                    )
                                }
                            }
                        }
                    }
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFF0064FF)
                    )
                }
                Column() {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {


                    }

                    Box {
                        var textState by remember { mutableStateOf("") }
                        val maxLength = 110
                        val lightBlue = Color(0xffd8e6ff)
                        val blue = Color(0xff76a9ff)

                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = textState,
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.White,
                                cursorColor = Color.LightGray,
                                disabledLabelColor = lightBlue,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            onValueChange = {
                                if (it.length <= maxLength) textState = it
                            },
                            shape = RectangleShape,

                            trailingIcon = {
                                if (textState.isNotEmpty()) {
                                    IconButton(onClick = { textState = "" }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Close,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        )
                        Text(
                            text = "${textState.length} / $maxLength",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp)
                                .align(Alignment.BottomEnd),
                            textAlign = TextAlign.End,
                            color = blue
                        )
                    }

                }
            }

        }
        Spacer(
            Modifier
                .height(
                    WindowInsets.ime.asPaddingValues()
                        .calculateBottomPadding() - WindowInsets.navigationBars.asPaddingValues()
                        .calculateBottomPadding()
                )

        )
    }
}


@Preview
@Composable
fun ChatFindScreenPreview() {
    ChatFindScreen()
}
