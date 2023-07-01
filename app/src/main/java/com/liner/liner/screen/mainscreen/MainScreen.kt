package com.liner.liner.screen.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
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
            (viewModel.state.value as MainScreenState.Loaded).firstCardUser,
            (viewModel.state.value as MainScreenState.Loaded).secondCardUser,
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
private fun CardStack(
    frontCard: User?,
    backCard: User?,
    swipeLeft: () -> Unit,
    swipeRight: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        backCard?.let {
            UserProfileCard(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(5.dp),
                user = backCard
            )
        }
        frontCard?.let {
            UserProfileCard(
                modifier = Modifier
                    .align(Alignment.Center)
                    .swipeAway({}, {}, { swipeLeft.invoke() }, { swipeRight.invoke() },{})
                    .pointerInput(Unit){

                    }
                    .padding(5.dp),
                user = frontCard
            )
        }
        ControlButtonPanel(modifier = Modifier.align(Alignment.BottomCenter), swipeLeft)
    }
}

@Composable
fun ControlButtonPanel(modifier: Modifier = Modifier, onCloseButton: () -> Unit) {
    Row(modifier = modifier) {
        Icon(
            Icons.Default.Close,
            contentDescription = "fav",
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .clickable { onCloseButton.invoke() })
        Icon(Icons.Filled.Cancel, contentDescription = "fav")
        Icon(Icons.Filled.Star, contentDescription = "fav")
        Icon(Icons.Filled.Favorite, contentDescription = "fav")
        Icon(Icons.Filled.Thunderstorm, contentDescription = "fav")
    }
}

@Composable
fun UserProfileCard(modifier: Modifier = Modifier, user: User) {

    Card(modifier = modifier.offset()) {
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
