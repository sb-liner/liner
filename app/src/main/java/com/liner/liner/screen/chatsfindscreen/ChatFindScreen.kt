package com.liner.liner.screen.chatsfindscreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.liner.liner.R
import com.liner.liner.data.model.ChatMessage
import com.liner.liner.data.model.User
import com.liner.liner.swipeAway
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

val TAG = "MainScreen"

@Composable
fun ChatFindScreen(
    viewModel: ChatFindViewModel = hiltViewModel()
) {

    when (viewModel.state.value) {
        is ChatFindScreenState.Loading -> LoadingScreen()
        is ChatFindScreenState.Loaded -> Row() {}
        is ChatFindScreenState.Matched -> ChatCard(
            User(
                (viewModel.state.value as ChatFindScreenState.Matched).matchedUserId,
                emptyList(),
                0,
                25
            ), viewModel
        )
        is ChatFindScreenState.Connected -> ChatCard(User("Kim", emptyList(), 0, 25), viewModel)
        is ChatFindScreenState.Error -> ChatCard(
            User(
                (viewModel.state.value as ChatFindScreenState.Error).errorMessage,
                emptyList(),
                0,
                25
            ), viewModel
        )
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
private fun ChatCard(user: User, viewModel: ChatFindViewModel) {

    var messageList = remember { viewModel.chatMessage }

    val scrollState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .weight(1f)
                .swipeAway(
                    { viewModel.findNewMatch() },
                    { viewModel.findNewMatch() },
                    { viewModel.findNewMatch() },
                    { viewModel.findNewMatch() },
                    {}),
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
                        Box { // 프로필 사진과 이름 텍스트
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
                                    Text(text = user.id, maxLines = 1)
                                    // Add a vertical space between the author and message texts
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = user.age.toString())
                                }
                            }
                        }
                        Box(modifier = Modifier.weight(1f)) {// 책갈피 버튼
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                ElevatedButton(onClick = { }) {
                                    Icon(
                                        painterResource(id = R.drawable.baseline_bookmark_border_24),
                                        "save for later"
                                    )
                                }
                            }
                        }
                    }
                    LinearProgressIndicator( // 로딩바
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFF0064FF)
                    )
                }
                Column() {

                    val scope = rememberCoroutineScope()
                    LazyColumn( // 채팅창
                        state = scrollState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 16.dp)
                            .pointerInput(Unit) {
                                awaitPointerEventScope {
                                    while (true) {
                                        val pointer = awaitFirstDown(
                                            pass = PointerEventPass.Main
                                        )
                                        val pointerId = pointer.id
                                        drag(pointerId) { change ->
                                            val yy = change.positionChange().y
                                            scope.launch {
                                                scrollState.scroll {
                                                    scrollBy(-yy)
                                                }
                                            }
                                            change.consume()
                                        }
                                        pointer.consume()

                                    }
                                }
                            },
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(messageList.toList()) {
                            if (it.status < 5) {
                                SentMessage(it)
                            } else {
                                ReceivedMessage(it)
                            }
                        }

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
                                    IconButton(onClick = {
                                        viewModel.sendMsg(textState)
                                        textState = ""
                                    }) {
                                        Icon(
                                            imageVector = Icons.Outlined.ArrowUpward,
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

@Composable
fun ReceivedMessage(it: ChatMessage) {
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun SentMessage(chatMessage: ChatMessage) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Card(shape = RoundedCornerShape(percent = 50)) {
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .padding(8.dp)
            ) {
                Text(chatMessage.msg, color = Color.Black)
            }
        }
    }
}


@Preview
@Composable
fun ChatFindScreenPreview() {
    ChatFindScreen()
}
