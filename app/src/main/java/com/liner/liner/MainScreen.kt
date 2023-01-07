package com.liner.liner

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

val TAG = "MainScreen"


@Composable
fun MainScreen() {
    val list = listOf(painterResource(id = R.drawable.pichu),
        painterResource(id = R.drawable.dummy_image)
    )
    Box(modifier = Modifier.fillMaxSize()) {
        for (img in list.reversed()) {
            MovableCard(modifier = Modifier.align(Alignment.Center),img)
        }
    }
}

@Composable
fun MovableCard(modifier: Modifier = Modifier, painterResource: Painter) {

    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val rotation = remember { Animatable(0f) }
    var isTouchOnUpperSide by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .graphicsLayer(
                translationX = offsetX.value,
                translationY = offsetY.value,
                rotationZ = rotation.value
            )
            .pointerInput(Unit) {
                val centerHeight = this.size.height / 2

                detectTapGestures(
                    onPress = {
                        isTouchOnUpperSide = it.y > centerHeight
                    }
                )
            }
            .pointerInput(Unit) {
                coroutineScope {
                    while (true) {
                        val pointerId = awaitPointerEventScope { awaitFirstDown().id }
                        offsetX.stop()
                        awaitPointerEventScope {
                            drag(pointerId) { change ->
                                val xOffset = offsetX.value + change.positionChange().x
                                val yOffset = offsetY.value + change.positionChange().y
                                launch {
                                    offsetX.snapTo(xOffset)
                                    offsetY.snapTo(yOffset)
                                    rotation.snapTo(
                                        offsetX.value * 0.01f *
                                                if (isTouchOnUpperSide) -1f else 1f
                                    )
                                }
                                change.consume()
                            }
                        }
                        launch { offsetX.animateTo(0f) }
                        launch { offsetY.animateTo(0f) }
                        launch { rotation.animateTo(0f) }
                    }
                }
            }
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
