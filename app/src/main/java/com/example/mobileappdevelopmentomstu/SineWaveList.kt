package com.example.mobileappdevelopmentomstu

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun SineWaveList(
    navController: NavHostController,
    functions: List<SineWave>,
    modifier: Modifier,
    onFunctionChange: (SineWave, Int) -> Unit,
    createFunction: () -> Unit,
    deleteFunction: () -> SineWave,
) {
    LazyColumn(modifier) {
        items(functions.size) { index ->
            Row {
                OutlinedTextField(
                    value = functions[index].name,
                    onValueChange = {
                        onFunctionChange(
                            functions[index].copy(name = it),
                            index
                        )
                    },
                    modifier = Modifier.width(200.dp)
                )
                Button(
                    { navController.navigate("wave/$index") },
                    modifier = Modifier
                        .width(100.dp)
                ) { Text("Edit") }
                Button(
                    { deleteFunction() },
                    modifier = Modifier.width(100.dp)
                ) { Text("Delete") }
            }
        }
        item {
            Button({ createFunction() }) { Text("+") }
        }
    }
}