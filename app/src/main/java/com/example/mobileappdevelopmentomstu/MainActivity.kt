package com.example.mobileappdevelopmentomstu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.mobileappdevelopmentomstu.ui.theme.MobileAppDevelopmentOMSTUTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileAppDevelopmentOMSTUTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun SamplingRateInput(samplingRate: Float, onSamplingRateChange: (Float) -> Unit) {
    TextField(
        value = samplingRate.toString(),
        onValueChange = { newRate -> onSamplingRateChange(newRate.toFloat()) },
        label = { Text("Sampling Rate") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun MainScreen() {
    var samplingRate by remember { mutableStateOf(100f) }
    var sineWaves by remember { mutableStateOf(listOf(SineWave(1f, 1f, 0f))) }

    Column {
        OscilloscopeScreen(
            functions = sineWaves,
            samplingRate = samplingRate,
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp)
        ) {
            SineWaveList(functions = sineWaves, onFunctionChange = { index, newWave ->
                sineWaves = sineWaves.toMutableList().apply {
                    this[index] = newWave
                }
            })

            SamplingRateInput(samplingRate = samplingRate, onSamplingRateChange = { newRate ->
                samplingRate = newRate
            })
        }
    }
}