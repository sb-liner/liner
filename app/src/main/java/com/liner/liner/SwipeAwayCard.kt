package com.liner.liner

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.tween
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.drag
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

fun Modifier.swipeAway(
    swipeUp: () -> Unit,
    swipeDown: () -> Unit,
    swipeLeft: () -> Unit,
    swipeRight: () -> Unit
): Modifier = composed {

    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val rotation = remember { Animatable(0f) }
    var isTouchOnUpperSide by remember { mutableStateOf(false) }


    pointerInput(Unit) {
        val centerHeight = this.size.height / 2

        detectTapGestures(
            onPress = {
                isTouchOnUpperSide = it.y > centerHeight
            }
        )
    }
        .pointerInput(Unit) {
            val decay = splineBasedDecay<Float>(this)
            coroutineScope {
                while (true) {
                    val pointerId = awaitPointerEventScope { awaitFirstDown().id }
                    offsetX.stop()
                    offsetY.stop()
                    rotation.stop()
                    val velocityTracker = VelocityTracker()
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
                            velocityTracker.addPosition(change.uptimeMillis, change.position)
                            change.consume()
                        }
                    }
                    // after release
                    // check
                    val velocityX = velocityTracker.calculateVelocity().x
                    val velocityY = velocityTracker.calculateVelocity().y
                    val targetOffsetX = decay.calculateTargetValue(offsetX.value, velocityX)
                    val targetOffsetY = decay.calculateTargetValue(offsetX.value, velocityY)
                    Log.d("TAG", "swipeAway: vx $velocityX vy $velocityY tx $targetOffsetX ty $targetOffsetY")
                    if (velocityX.absoluteValue > velocityY.absoluteValue && targetOffsetX.absoluteValue > size.width) {
                        val animation1 = launch { offsetX.animateTo(targetOffsetX, tween()) }
                        val animation2 = launch { offsetY.animateTo(targetOffsetY, tween()) }
                        joinAll(animation1, animation2)
                        if (targetOffsetX > 0) {
                            swipeRight()
                        } else {
                            swipeLeft()
                        }
                    } else if (velocityX.absoluteValue < velocityY.absoluteValue && targetOffsetY.absoluteValue > size.height) {
                        val animation1 = launch { offsetX.animateTo(targetOffsetX, tween()) }
                        val animation2 = launch { offsetY.animateTo(targetOffsetY, tween()) }
                        joinAll(animation1, animation2)
                        if (targetOffsetY > 0) {
                            swipeUp()
                        } else {
                            swipeDown()
                        }
                    } else {
                        launch { offsetX.animateTo(0f) }
                        launch { offsetY.animateTo(0f) }
                        launch { rotation.animateTo(0f) }
                    }
                }
            }
        }
        .graphicsLayer(
            translationX = offsetX.value,
            translationY = offsetY.value,
            rotationZ = rotation.value
        )
}
