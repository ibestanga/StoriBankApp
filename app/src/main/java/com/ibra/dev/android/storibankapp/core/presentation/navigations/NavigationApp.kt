package com.ibra.dev.android.storibankapp.core.presentation.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ibra.dev.android.storibankapp.login.presentation.screen.LoginScreen
import com.ibra.dev.android.storibankapp.register.presentations.screen.RegisterScreen

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginDestination
    ) {
//        composable<HomeDestination> {
//            TODO("Not yet implemented")
//        }

        composable<LoginDestination> {
            LoginScreen(navController)
        }

        composable<RegisterDestination> {
            RegisterScreen(navController)
        }
//
//        composable<TakePictureDniDestination> {
//            TODO("Not yet implemented")
//        }
    }
}