package com.example.jetweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.jetweatherapp.navigation.AppNavigation
import com.example.jetweatherapp.ui.home.HomeViewModel
import com.example.jetweatherapp.ui.theme.JetWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val controller = rememberNavController()
            JetWeatherAppTheme {
                val homeVM: HomeViewModel by viewModels()
                MyApp {
                    AppNavigation(navController = controller, homeViewModel = homeVM)
                }
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    JetWeatherAppTheme() {
        content()
    }
}
