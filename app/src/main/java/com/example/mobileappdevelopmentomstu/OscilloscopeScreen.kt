package com.example.mobileappdevelopmentomstu

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun OscilloscopeScreen(
    functions: List<SineWave>,
    samplingRate: Float,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()

    val timeOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing)
        )
    )

    Canvas(modifier = modifier.background(Color(0xFF463F3F))) {
        val width = size.width
        val height = size.height / 2

        val points = generateSineWavePoints(
            functions,
            width.toInt(),
            height.toInt(),
            timeOffset,
            samplingRate
        )

        val path = Path().apply {
            points.forEachIndexed { index, point ->
                if (index == 0) {
                    moveTo(point.x, point.y)
                } else {
                    lineTo(point.x, point.y)
                }
            }
        }

        drawPath(
            path = path,
            color = Color(0xFFFFC107).copy(alpha = 0.2f),
            style = Stroke(width = 12f),
            blendMode = BlendMode.SrcOver
        )
        drawPath(
            path = path,
            color = Color(0xFFFFC107).copy(alpha = 0.5f),
            style = Stroke(width = 6f),
            blendMode = BlendMode.SrcOver
        )
        drawPath(
            path = path,
            color = Color(0xFFFFC107),
            style = Stroke(width = 2f)
        )
    }
}