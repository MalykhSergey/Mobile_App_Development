package com.example.mobileappdevelopmentomstu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun SineWaveEditor(sineWave: SineWave, onValueChange: (SineWave) -> Unit) {
    Column {
        TextField(
            value = sineWave.amplitude.toString(),
            onValueChange = { newAmp -> onValueChange(sineWave.copy(amplitude = newAmp.toFloat())) },
            label = { Text("Amplitude") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        TextField(
            value = sineWave.frequency.toString(),
            onValueChange = { newFreq -> onValueChange(sineWave.copy(frequency = newFreq.toFloat())) },
            label = { Text("Frequency") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        TextField(
            value = sineWave.phase.toString(),
            onValueChange = { newPhase -> onValueChange(sineWave.copy(phase = newPhase.toFloat())) },
            label = { Text("Phase") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}