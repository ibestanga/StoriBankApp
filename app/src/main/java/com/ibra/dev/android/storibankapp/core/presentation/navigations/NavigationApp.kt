package com.ibra.dev.android.storibankapp.core.presentation.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ibra.dev.android.storibankapp.home.domain.models.MovementsDto
import com.ibra.dev.android.storibankapp.home.domain.models.TypeTransaction
import com.ibra.dev.android.storibankapp.home.presentation.screens.HomeView
import com.ibra.dev.android.storibankapp.home.presentation.screens.MovementsScreen
import com.ibra.dev.android.storibankapp.login.presentation.screen.LoginScreen
import com.ibra.dev.android.storibankapp.register.presentations.screen.form.SingUpScreen
import com.ibra.dev.android.storibankapp.register.presentations.screen.result.BobsitoState
import com.ibra.dev.android.storibankapp.register.presentations.screen.result.SingUpResultScreen

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginDestination
    ) {
        composable<HomeDestination> { backStackEntry ->
            val email: String = backStackEntry.toRoute<HomeDestination>().email
            HomeView(navController, email)
        }

        composable<LoginDestination> {
            LoginScreen(navController)
        }

        composable<RegisterDestination> {
            SingUpScreen(navController)
        }

        composable<SingUpResultDestination> { backStackEntry ->
            val msg: String = backStackEntry.toRoute<SingUpResultDestination>().msg
            val state: BobsitoState = backStackEntry.toRoute<SingUpResultDestination>().state
            SingUpResultScreen(navController, msg, state)
        }

        composable<MovementsDetailsDestination> { backStackEntry ->
            val typeTransaction: TypeTransaction =
                backStackEntry.toRoute<MovementsDetailsDestination>().typeTransaction
            val amount: Double? = backStackEntry.toRoute<MovementsDetailsDestination>().amount.toDoubleOrNull()
            val date: String = backStackEntry.toRoute<MovementsDetailsDestination>().date
            val description: String =
                backStackEntry.toRoute<MovementsDetailsDestination>().description
            val movement = MovementsDto(typeTransaction, amount, date, description)
            MovementsScreen(navController, movement)
        }
    }
}