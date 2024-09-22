package com.example.mobileappdevelopmentomstu

import androidx.compose.ui.geometry.Offset

fun generateSineWavePoints(
    functions: List<SineWave>,
    sampleRate: Float
): MutableList<Offset> {
    val points = mutableListOf<Offset>()
    var x = 0f
    while (x < 1) {
        val yValue =
            (functions.sumOf { func -> func.amplitude * kotlin.math.sin(2 * Math.PI * func.frequency * x + func.phase) }).toFloat()
        points.add(Offset(x, yValue))
        x += 0.001f
    }
    return points
}

fun stretchSineWavePoints(
    refPoints: List<Offset>,
    width: Int,
    height: Int
): MutableList<Offset> {
    val points = mutableListOf<Offset>()
    for (point in refPoints)
        points.add(
            Offset(
                (point.x) * width,
                height / 2 + point.y
            )
        )
    return points
}

fun getSineWavePoints(
    points: MutableList<Offset>,
    timerState: Int
){
    val temp = points[0]
    for (i in 0 until points.size - 1) {
        points[i] = Offset(points[i].x, points[i + 1].y)
    }
    points[points.lastIndex] = Offset(points[points.lastIndex].x, temp.y)
}