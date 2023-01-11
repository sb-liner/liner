package com.liner.liner

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val TAG = "MainScreen"


@Composable
fun MainScreen() {
    val a = painterResource(id = R.drawable.pichu)
    val b = painterResource(id = R.drawable.dummy_image)
    val list = remember {
        mutableStateOf(
            mutableListOf(
                a, b
            )
        )
    }
    Box(modifier = Modifier.fillMaxSize()) {
        for (index in list.value.size -1 downTo 0) {
            MovableCard(
                modifier = Modifier
                    .align(Alignment.Center)
                    .swipeAway({}, {}, {}, {
                        list.value = list.value.drop(1).toMutableList()
                        Log.d(TAG, "MainScreen: right")
                        Log.d(TAG, "MainScreen: $list")
                    }), list.value[index]
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
