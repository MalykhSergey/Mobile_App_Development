package com.example.mobileappdevelopmentomstu

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.math.sin

var audioTrack = AudioTrack(
    AudioManager.STREAM_MUSIC,
    44100,
    AudioFormat.CHANNEL_OUT_MONO,
    AudioFormat.ENCODING_PCM_16BIT,
    44100 * 100,
    AudioTrack.MODE_STATIC
)
var is_playing by mutableStateOf(false)

fun playSineWave(sineWaves: List<SineWave>) {
    var x = 0f

    val duration = 1 / sineWaves.minOf { func -> func.frequency }
    val sampleRate = 44100
    val numSamples = (duration * sampleRate).toInt()
    val sample = ShortArray(numSamples)
    val Fs = 1f / sampleRate
    val ampSum = sineWaves.sumOf { func -> func.amplitude.toDouble() }
    for (i in 0 until numSamples) {
        val y =
            sineWaves.sumOf { func -> func.amplitude * sin(2 * Math.PI * func.frequency * x + func.phase * 2 * Math.PI) }
        x += Fs
        sample[i] = (1*(y * Short.MAX_VALUE / ampSum)).toInt().toShort()
    }
    audioTrack.write(sample, 0, numSamples)
    audioTrack.setLoopPoints(0, numSamples, -1)
    audioTrack.play()
}
