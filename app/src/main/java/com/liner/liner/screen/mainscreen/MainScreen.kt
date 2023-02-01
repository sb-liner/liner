package com.liner.liner.screen.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.liner.liner.R
import com.liner.liner.data.model.User
import com.liner.liner.swipeAway

val TAG = "MainScreen"


@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {

    when (viewModel.state.value) {
        is MainScreenState.Loading -> LoadingScreen()
        is MainScreenState.Loaded -> CardStack(viewModel.userList.toList(), viewModel::swipeRight)
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
private fun CardStack(list: List<User>, swipeRight: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        for (index in list.size - 1 downTo 0) {
            MovableCard(
                modifier = Modifier
                    .align(Alignment.Center)
                    .swipeAway({}, {}, {}, {
                        swipeRight.invoke()
                    }), painterResource(id = list[index].picture)
            )
        }
    }
}

@Composable
fun MovableCard(modifier: Modifier = Modifier, painterResource: Painter) {

    Card(
        modifier = modifier
            .width(400.dp)
            .height(650.dp)
    ) {
        Image(
            painter = painterResource, contentDescription = "Profile Image",
            modifier = Modifier.fillMaxSize()
        )
    }
}


@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}
