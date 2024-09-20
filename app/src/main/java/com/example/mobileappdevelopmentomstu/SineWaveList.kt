package com.example.mobileappdevelopmentomstu

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable

@Composable
fun SineWaveList(functions: List<SineWave>, onFunctionChange: (Int, SineWave) -> Unit) {
    LazyColumn {
        items(functions.size) { index ->
            SineWaveEditor(
                sineWave = functions[index],
                onValueChange = { newWave -> onFunctionChange(index, newWave) }
            )
        }
    }
}