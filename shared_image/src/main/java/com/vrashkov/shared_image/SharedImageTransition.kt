package com.vrashkov.shared_image

import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun SharedImageTransition(
    sharedImageFlow: SharedFlow<SharedImageDataEvent>,
    sharedImageAnimateEventFlow: SharedFlow<SharedImageAnimateEvent>
) {

    val imageData = remember { mutableStateOf(TransitionData())}

    var isForward by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(false)}

    LaunchedEffect(key1 = Unit) {
        launch {
            sharedImageFlow.collect { data ->
                when(data) {
                    is SharedImageDataEvent.BackwardImage -> {
                        imageData.value = imageData.value.copy(
                            backwardImage = data.newValue
                        )
                    }
                    is SharedImageDataEvent.ForwardImage -> {
                        imageData.value = imageData.value.copy(
                            forwardImage = data.newValue
                        )
                    }
                    is SharedImageDataEvent.SetImage -> {
                        imageData.value = imageData.value.copy(
                            imageId = data.newValue
                        )
                    }
                }
            }
        }
        launch {
            sharedImageAnimateEventFlow.collect { event ->
                when(event) {
                    SharedImageAnimateEvent.AnimateBackward -> {
                        isForward = false
                    }
                    SharedImageAnimateEvent.AnimateForward -> {
                        isForward = true
                    }
                    SharedImageAnimateEvent.StartAnimation -> {
                        isVisible = true
                    }
                    SharedImageAnimateEvent.StopAnimation -> {
                        isVisible = false
                    }
                }
            }
        }
    }

    val targetValue = if (isVisible) {
        if (isForward) 1f else 0f
    } else {
        0f
    }

    val animationValue: Float by animateFloatAsState(
        targetValue = targetValue,
        animationSpec = tween(1500),
        finishedListener = {

            // this is just an example that we should hide the cloned product image after the animation ends
            // it will probably be specific, depending how you want to handle that
            if (!isForward) {
                isVisible = false
            }
        }
    )

    if (isVisible) {
        val startData = if (isForward) {
            imageData.value.forwardImage
        } else {
            imageData.value.backwardImage
        }

        val endData = if (isForward) {
            imageData.value.backwardImage
        } else {
            imageData.value.forwardImage
        }

        val image = imageData.value.imageId

        var currentWidth: Float = measureProductData(startData!!.width, endData!!.width,animationValue, isForward)
        var currentHeight: Float = measureProductData(startData!!.height, endData!!.height,animationValue, isForward)

        var currentX: Float = measureProductData(startData!!.positionX, endData!!.positionX,animationValue, isForward)
        var currentY: Float = measureProductData(startData!!.positionY, endData!!.positionY,animationValue, isForward)

        Image(
            painterResource(id = image!!),
            contentDescription = null,
            modifier = Modifier
                .width(
                    with(LocalDensity.current) { currentWidth.toDp() }
                )
                .height(
                    with(LocalDensity.current) { currentHeight.toDp() }
                )
                .absoluteOffset {
                    IntOffset(currentX.toInt(),currentY.toInt())
                }
        )
    }
}

fun measureProductData(start: Float, end: Float, animationValue: Float, isForward: Boolean) : Float {
    var startLocal = start
    var endLocal = end

    if (!isForward) {
        startLocal = end
        endLocal = start
    }

    if (startLocal < endLocal) {
        return (startLocal + (animationValue * (endLocal - startLocal)))
    } else {
        return (startLocal - (animationValue * (startLocal - endLocal)))
    }
}

sealed class SharedImageDataEvent {
    data class SetImage(val newValue: Int) : SharedImageDataEvent()
    data class ForwardImage(val newValue: SharedImageData) : SharedImageDataEvent()
    data class BackwardImage(val newValue: SharedImageData) : SharedImageDataEvent()
}

sealed class SharedImageAnimateEvent {
    object AnimateForward: SharedImageAnimateEvent()
    object AnimateBackward: SharedImageAnimateEvent()

    object StartAnimation: SharedImageAnimateEvent()
    object StopAnimation: SharedImageAnimateEvent()
}

data class SharedImageData(
    val width: Float = 0f,
    val height: Float = 0f,
    val positionX: Float = 0f,
    val positionY: Float = 0f
)

data class TransitionData(
    val imageId: Int? = null,
    val forwardImage: SharedImageData? = null,
    val backwardImage: SharedImageData? = null,
)