package com.liner.liner.screen.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.liner.liner.data.model.User
import com.liner.liner.swipeAway

val TAG = "MainScreen"

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {

    when (viewModel.state.value) {
        is MainScreenState.Loading -> LoadingScreen()
        is MainScreenState.Loaded -> CardStack(
            viewModel.userList.toList(),
            viewModel::nope,
            viewModel::like
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

@Composable
private fun CardStack(list: List<User>, swipeLeft: () -> Unit, swipeRight: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 5.dp, vertical = 5.dp)
    ) {
        for (user in list.asReversed()) {
            UserProfileCard(
                modifier = Modifier
                    .align(Alignment.Center)
                    .swipeAway({}, {}, { swipeLeft.invoke() }, {
                        swipeRight.invoke()
                    }), user
            )
        }
        ControlButtonPanel(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun ControlButtonPanel(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Icon(Icons.Default.Close, contentDescription = "fav")
        Icon(Icons.Filled.Cancel, contentDescription = "fav")
        Icon(Icons.Filled.Star, contentDescription = "fav")
        Icon(Icons.Filled.Favorite, contentDescription = "fav")
        Icon(Icons.Filled.Thunderstorm, contentDescription = "fav")
    }
}

@Composable
fun UserProfileCard(modifier: Modifier = Modifier, user: User) {

    Card(modifier = modifier) {
        Box {
            Image(
                painter = painterResource(id = user.picture), contentDescription = "Profile Image",
                modifier = Modifier
                    .fillMaxSize()
                    .drawWithCache {
                        val gradient = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = size.height * 3 / 5,
                            endY = size.height - 60.dp.toPx()
                        )
                        onDrawWithContent {
                            drawContent()
                            drawRect(gradient, blendMode = BlendMode.Multiply)
                        }
                    }
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 15.dp, bottom = 80.dp)
            ) {
                Row {
                    Text(
                        text = user.id,
                        fontSize = 46.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.alignByBaseline()
                    )
                    Text(
                        text = user.age.toString(),
                        fontSize = 30.sp,
                        color = Color.White,
                        modifier = Modifier
                            .padding(start = 14.dp)
                            .alignByBaseline()
                    )
                }
                Text(text = "description", color = Color.White)
                Text(text = "description", color = Color.White)
            }
        }
    }
}


@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}
