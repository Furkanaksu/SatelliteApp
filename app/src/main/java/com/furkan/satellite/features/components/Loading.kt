package com.furkan.satellite.features.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.furkan.satellite.R




@Composable
fun LoadingBar(
    isDisplayed: Boolean,
    dismissOnBackPress: Boolean = true,
) {
    AnimatedVisibility(visible = isDisplayed) {
        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = dismissOnBackPress,
            ),
            onDismissRequest = { }
        ) {
            Box(modifier = Modifier) {
                LoadingAnimation()
            }
        }
    }
}


@Composable
fun LoadingAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column {
            LottieAnimation(
                composition,
                modifier = Modifier.size(50.dp),
                restartOnPlay = true,
                alignment = Alignment.Center,
                iterations = LottieConstants.IterateForever,
            )
        }
    }
}