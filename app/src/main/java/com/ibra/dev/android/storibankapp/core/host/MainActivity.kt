package com.ibra.dev.android.storibankapp.core.host

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.ibra.dev.android.storibankapp.login.data.datasource.LoginRemoteDataSourceImpl.Companion.CLIENTS_COLLECTION
import com.ibra.dev.android.storibankapp.login.data.datasource.LoginRemoteDataSourceImpl.Companion.TAG
import com.ibra.dev.android.storibankapp.login.presentation.viewmodel.LoginViewModel
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
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val viewModel: LoginViewModel = hiltViewModel()

    Text(
        text = "Hello $name!",
        modifier = modifier.clickable {
            viewModel.login("ibra@test.com","12345")
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StoriBankAppTheme {
        Greeting("Android")
    }
}