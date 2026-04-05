package com.umain.seatmap.ui

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun AnimatableItem(
    visible: Boolean,
    enterDelay: Int,
    exitDelay: Int,
    content: @Composable () -> Unit,
) {
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.4f,
        animationSpec =
            if (visible) {
                spring(
                    dampingRatio = 0.6f,
                    stiffness = Spring.StiffnessLow,
                )
            } else {
                tween(
                    durationMillis = 250,
                    delayMillis = exitDelay,
                    easing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f),
                )
            },
        label = "scale",
    )

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec =
            tween(
                durationMillis = if (visible) 300 else 150,
                delayMillis = if (visible) enterDelay else exitDelay,
            ),
        label = "alpha",
    )

    if (alpha > 0f || visible) {
        Box(
            modifier =
                Modifier.graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    this.alpha = alpha
                },
        ) {
            content()
        }
    } else {
        Box(modifier = Modifier.graphicsLayer { this.alpha = 0f }) { content() }
    }
}
