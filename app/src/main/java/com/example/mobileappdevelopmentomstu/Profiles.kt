package com.example.mobileappdevelopmentomstu

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Profile(
    var name: String,
    val sineWaves: List<SineWave>
)

@Composable
fun Profiles(profiles: MutableList<Profile>, onProfileChange: (String, Int) -> Unit) {
    LazyColumn(Modifier.padding(vertical = 30.dp)) {
        items(profiles.size) { index ->
            Row {
                OutlinedTextField(
                    com.example.mobileappdevelopmentomstu.profiles[index].name,
                    onValueChange = { onProfileChange(it, index) })
                Button(onClick = {}) { Text("Open") }
                Button(onClick = {}) { Text("Delete") }
            }
        }
    }
}