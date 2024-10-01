package com.ibra.dev.android.storibankapp.core.presentation.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ibra.dev.android.storibankapp.home.presentation.screens.HomeView
import com.ibra.dev.android.storibankapp.login.presentation.screen.LoginScreen
import com.ibra.dev.android.storibankapp.register.presentations.screen.CameraScreen
import com.ibra.dev.android.storibankapp.register.presentations.screen.SingUpScreen

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginDestination
    ) {
        composable<HomeDestination> {
            HomeView(navController)
        }

        composable<LoginDestination> {
            LoginScreen(navController)
        }

        composable<RegisterDestination> {
            SingUpScreen(navController)
        }

        composable<TakePictureDniDestination> {
           CameraScreen(navController)
        }
    }
}