package com.example.mobileappdevelopmentomstu

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun OscilloscopeScreen(
    points: MutableList<Offset>,
    modifier: Modifier = Modifier
) {
    var timerState by remember { mutableStateOf(1) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(100)
            timerState = -timerState

        }
    }
    Canvas(
        modifier = modifier
            .padding(10.dp)
            .background(Color(0xFF463F3F))
    ) {
        getSineWavePoints(points, timerState)

        drawPoints(
            points = points,
            strokeWidth = 4f,
            pointMode = PointMode.Polygon,
            color = Color(0xFFFFFB07),
            blendMode = BlendMode.SrcOver
        )

        drawPoints(
            points = points,
            strokeWidth = 8f,
            pointMode = PointMode.Polygon,
            color = Color(0xFFFFC107).copy(alpha = 0.5f),
            blendMode = BlendMode.SrcOver
        )
        drawPoints(
            points = points,
            strokeWidth = 12f,
            pointMode = PointMode.Polygon,
            color = Color(0xFFFFC107).copy(alpha = 0.2f),
            blendMode = BlendMode.SrcOver
        )
    }
}