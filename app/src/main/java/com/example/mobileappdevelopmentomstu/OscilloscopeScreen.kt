package com.example.mobileappdevelopmentomstu

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.sin


@Composable
fun SinWaveScreen(
    sineWave: SineWave,
    onFunctionChange: (SineWave) -> Unit,
    scale: Int,
    scaleChangeFunction: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 30.dp)
    ) {
        Text(sineWave.name)
        Box(
            modifier = Modifier
                .weight(2f)
                .padding(10.dp)
        ) {
            OscilloscopeScreen(
                listOf(sineWave), Modifier.fillMaxSize(), scale, scaleChangeFunction
            )
            Column(
                Modifier
                    .align(Alignment.TopCenter)
                    .padding(horizontal = 50.dp)
            ) {
                OutlinedTextField(
                    value = "%.1f".format(sineWave.phase),
                    onValueChange = { onFunctionChange(sineWave.copy(phase = it.toFloat())) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .width(80.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Slider(
                    value = sineWave.phase,
                    onValueChange = { onFunctionChange(sineWave.copy(phase = it)) },
                    Modifier.fillMaxWidth()
                )
            }
            OutlinedTextField(
                value = "%.1f".format(sineWave.amplitude),
                onValueChange = { onFunctionChange(sineWave.copy(amplitude = it.toFloat())) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(80.dp)
            )
            Slider(value = sineWave.amplitude,
                valueRange = -100f..100f,
                onValueChange = { onFunctionChange(sineWave.copy(amplitude = it)) },
                modifier = Modifier
                    .graphicsLayer {
                        rotationZ = 270f
                        transformOrigin = TransformOrigin(0f, 0f)
                    }
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(
                            Constraints(
                                minWidth = constraints.minHeight,
                                maxWidth = constraints.maxHeight,
                                minHeight = constraints.minWidth,
                                maxHeight = constraints.maxWidth,
                            )
                        )
                        layout(placeable.height, placeable.width) {
                            placeable.place(-placeable.width, 0)
                        }
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp))
            Column(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 50.dp, vertical = 50.dp)
            ) {
                Slider(
                    value = sineWave.frequency,
                    valueRange = 0f..20f * scale,
                    onValueChange = { onFunctionChange(sineWave.copy(frequency = it)) },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = "%.1f".format(sineWave.frequency),
                    onValueChange = {
                        onFunctionChange(
                            sineWave.copy(frequency = it.replace(",", ".").toFloat())
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .width(80.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun OscilloscopeScreen(
    sineWaves: List<SineWave>,
    modifier: Modifier = Modifier,
    scale: Int,
    changeScaleFunction: () -> Unit = {}
) {
    var timerState by remember { mutableStateOf(0f) }
    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos { _ ->
                timerState += 0.005f
            }
        }
    }
    var canvasSize by remember { mutableStateOf(IntSize(100, 100)) }
    val scope = rememberCoroutineScope()
    val points = remember(canvasSize) {
        MutableList(canvasSize.width) { i ->
            Offset(
                i.toFloat(), (canvasSize.height / 2).toFloat()
            )
        }
    }
    Box(modifier) {
        Canvas(
            Modifier
                .fillMaxSize()
                .onSizeChanged { size ->
                    canvasSize = size
                }) {
            val y =
                size.height / 2 + (sineWaves.sumOf { func -> func.amplitude * sin(2 * Math.PI * func.frequency * timerState / scale + func.phase * 2 * Math.PI) }).toFloat()
            points[points.lastIndex] = Offset(points[points.lastIndex].x, y)
            shiftSineWavePoints(points)
            drawPoints(
                points = points,
                strokeWidth = 4f,
                pointMode = PointMode.Polygon,
                color = Color(0xFFFFFB07),
            )
            drawPoints(
                points = points,
                strokeWidth = 8f,
                pointMode = PointMode.Polygon,
                color = Color(0xFFFFC107).copy(alpha = 0.5f),
            )
            drawPoints(
                points = points,
                strokeWidth = 12f,
                pointMode = PointMode.Polygon,
                color = Color(0xFFCC9900).copy(alpha = 0.2f),
            )
        }
        Button(
            onClick = {
                if (!is_playing)
                    scope.launch(Dispatchers.Default) {
                        playSineWave(sineWaves)
                    }
                else
                    audioTrack.stop()
                is_playing = !is_playing
            },
            Modifier.align(Alignment.Center)
        ) { Text(if (!is_playing) "Play" else "Stop") }
        Button(onClick = changeScaleFunction, Modifier.align(Alignment.TopEnd)) { Text("x $scale") }
    }
}

private fun shiftSineWavePoints(
    points: MutableList<Offset>
) {
    for (i in 0 until points.size - 1) {
        points[i] = Offset(points[i].x, points[i + 1].y)
    }
}

