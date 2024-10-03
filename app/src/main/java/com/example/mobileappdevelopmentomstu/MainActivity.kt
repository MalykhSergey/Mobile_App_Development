package com.example.mobileappdevelopmentomstu

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

var sinWavesGlobal = mutableStateListOf(
    SineWave(
        "Sin 1", 100f, 1f, 0f
    ), SineWave(
        "Sin 2", 50f, 300f, 0f
    ), SineWave(
        "Sin 3", 25f, 600f, 0f
    )
)

lateinit var settings: SharedPreferences
var profiles =  mutableStateListOf(Profile("Default", sinWavesGlobal))


class MainActivity : ComponentActivity() {


    @SuppressLint("MutableCollectionMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        settings = getSharedPreferences("SinWaves", MODE_PRIVATE)
        val builder = GsonBuilder()
        val gson = builder.create()
        val temp = settings.getString("Profiles", "")
        if (temp != "") {
            val list_profiles: MutableList<Profile> =
                gson.fromJson(
                    temp,
                    object : TypeToken<MutableList<Profile>>() {}.type
                )
            profiles.clear()
            profiles.addAll(list_profiles)
            sinWavesGlobal.clear()
            sinWavesGlobal.addAll(profiles[0].sineWaves)
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.navigationBarColor = Color(0xFF463F3F).toArgb()
            val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
            windowInsetsController.isAppearanceLightNavigationBars = false
        }
        setContent {
            val navController = rememberNavController()
            var scale by remember { mutableStateOf(100) }
            val scaleChangeFunction = {
                if (scale == 1000) {
                    scale = 1
                } else scale *= 10
            }
            val sineWaves = remember {
                sinWavesGlobal
            }
            AppTheme {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color(0xFF463F3F))
                )
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        MainScreen(navController, sineWaves, { newWave, index ->
                            sineWaves[index] = newWave
                        }, {
                            sineWaves.add(SineWave("Sin ${sineWaves.size + 1}", 50f, 1f, 0f));
                        }, { sineWaves.removeAt(sineWaves.lastIndex) }, scale, scaleChangeFunction)
                    }
                    composable("wave/{waveId}") { stackEntry ->
                        val waveId = stackEntry.arguments?.getString("waveId")!!.toInt()
                        SinWaveScreen(
                            sineWaves[waveId], onFunctionChange = { newWave ->
                                sineWaves[waveId] = newWave
                            }, scale, scaleChangeFunction
                        )
                    }
                    composable("profiles") {
                        Profiles(profiles) { name, index ->
                            profiles[index] = profiles[index].copy(name = name)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    navController: NavHostController,
    sineWaves: MutableList<SineWave>,
    onFunctionChange: (SineWave, Int) -> Unit,
    createFunction: () -> Unit,
    deleteFunction: () -> SineWave,
    scale: Int,
    scaleChangeFunction: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(Modifier.offset(0.dp, 30.dp)) {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.Menu, contentDescription = "Показать меню", tint = Color.White)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                onClick = { navController.navigate("profiles") },
                text = { Text("Загрузить") })
            DropdownMenuItem(onClick = {
                val prefEditor: SharedPreferences.Editor = settings.edit()
                val builder = GsonBuilder()
                val gson = builder.create()
                prefEditor.putString("Profiles", gson.toJson(profiles))
                prefEditor.apply()
            }, text = { Text("Сохранить") })
        }
    }
    Column(
        Modifier
            .padding(0.dp, 30.dp, 0.dp, 50.dp)
            .fillMaxSize()
    ) {
        OscilloscopeScreen(
            sineWaves,
            Modifier
                .weight(2f)
                .fillMaxWidth(), scale, scaleChangeFunction
        )
        SineWaveList(
            navController,
            sineWaves,
            Modifier.weight(1f),
            onFunctionChange,
            createFunction,
            deleteFunction
        )
    }
}