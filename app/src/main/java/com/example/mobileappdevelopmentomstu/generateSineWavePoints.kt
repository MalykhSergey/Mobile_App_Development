package com.example.mobileappdevelopmentomstu

import androidx.compose.ui.geometry.Offset

fun generateSineWavePoints(
    functions: List<SineWave>,
    width: Int,
    height: Int,
    timeOffset: Float,
    samplingRate: Float
): List<Offset> {
    val points = mutableListOf<Offset>()
    for (x in 0 until width) {
        val time = x+timeOffset
        val yValue =
            functions.sumOf { func -> func.amplitude * kotlin.math.sin(2 * Math.PI * func.frequency * time + func.phase) }
                .toFloat()
        points.add(Offset(x.toFloat(), height - yValue * height/10))
    }
    return points
}