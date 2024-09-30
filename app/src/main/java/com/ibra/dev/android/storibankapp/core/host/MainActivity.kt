package com.ibra.dev.android.storibankapp.core.host

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ibra.dev.android.storibankapp.login.presentation.viewmodel.LoginViewModel
import com.ibra.dev.android.storibankapp.register.presentations.viewmodels.RegisterScreenViewModel
import com.ibra.dev.android.storibankapp.ui.theme.StoriBankAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StoriBankAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val registerViewModel: RegisterScreenViewModel = hiltViewModel()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            loginViewModel.login("ida.saxton.mckinley@example-pet-store.com", "123456")
        }) {
            Text(text = "Login")
        }

        Button(onClick = {
            registerViewModel.registerUser()
        }) {
            Text(text = "Register User")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StoriBankAppTheme {
        Greeting()
    }
}