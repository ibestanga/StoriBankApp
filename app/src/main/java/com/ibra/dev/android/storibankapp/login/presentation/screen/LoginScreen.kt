package com.ibra.dev.android.storibankapp.login.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ibra.dev.android.storibankapp.login.presentation.viewmodel.LoginViewModel


@Composable
fun LoginScreen(navController: NavController) {

    val loginViewModel: LoginViewModel = hiltViewModel()

    val stateScreen by loginViewModel.loginScreenEventsStateFlow.collectAsState()


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            loginViewModel.tryLogin("ida.saxton.mckinley@example-pet-store.com", "123456")
        }) {
            Text(text = "Login")
        }
    }
}