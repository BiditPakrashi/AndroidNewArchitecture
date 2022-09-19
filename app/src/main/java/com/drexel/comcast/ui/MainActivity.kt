package com.drexel.comcast.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.drexel.comcast.ui.films.MainScreen
import com.drexel.comcast.ui.login.LoginViewModel
import com.drexel.comcast.ui.navigation.NavigationScreen
import com.drexel.comcast.ui.theme.MealzAppTheme

class MainActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MealzAppTheme {
                NavigationScreen(viewModel = viewModel)
            }
        }
    }
}
