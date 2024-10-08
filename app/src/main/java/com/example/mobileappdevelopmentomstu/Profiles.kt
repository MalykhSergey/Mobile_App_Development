package com.example.mobileappdevelopmentomstu

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class Profile(
    var name: String,
    var sineWaves: MutableList<SineWave> = mutableListOf<SineWave>()
)

@Composable
fun Profiles(
    navController: NavController,
    profiles: MutableList<Profile>,
    onProfileChange: (String, Int) -> Unit,
    onProfileCreate: () -> Unit,
    onProfileDelete: (Int) -> Unit
) {
    LazyColumn(Modifier.padding(vertical = 30.dp)) {
        items(profiles.size) { index ->
            Row {
                OutlinedTextField(
                    profiles[index].name,
                    onValueChange = { onProfileChange(it, index) }, Modifier.width(200.dp)
                )
                Button(onClick = {
                    currentProfile.sineWaves = sinWavesGlobal.toMutableList()
                    sinWavesGlobal.clear()
                    sinWavesGlobal.addAll(profiles[index].sineWaves)
                    currentProfile = profiles[index]
                    navController.navigate("main")
                }, Modifier.width(100.dp)) { Text("Open") }
                Button(
                    onClick = { onProfileDelete(index) },
                    Modifier.width(100.dp)
                ) { Text("Delete") }
            }
        }
        item { Button(onClick = { onProfileCreate() }) { Text("+") } }
    }
}