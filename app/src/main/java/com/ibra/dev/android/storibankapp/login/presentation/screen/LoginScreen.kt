package com.ibra.dev.android.storibankapp.login.presentation.screen

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ibra.dev.android.storibankapp.R
import com.ibra.dev.android.storibankapp.core.presentation.navigations.HomeDestination
import com.ibra.dev.android.storibankapp.core.presentation.navigations.RegisterDestination
import com.ibra.dev.android.storibankapp.core.presentation.widgets.MyButton
import com.ibra.dev.android.storibankapp.core.presentation.widgets.MyFormTextField
import com.ibra.dev.android.storibankapp.core.presentation.widgets.MyPasswordTextField
import com.ibra.dev.android.storibankapp.login.presentation.states.LoginStates
import com.ibra.dev.android.storibankapp.login.presentation.viewmodel.LoginViewModel
import com.ibra.dev.android.storibankapp.ui.theme.mediumPadding
import com.ibra.dev.android.storibankapp.ui.theme.xLargePadding


@Composable
fun LoginScreen(navController: NavController) {

    val loginViewModel: LoginViewModel = hiltViewModel()

    val stateScreen by loginViewModel.loginScreenEventsStateFlow.collectAsState()

    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(key1 = stateScreen) {
        when (val state = stateScreen) {
            is LoginStates.Error -> {
                isLoading = false
                Toast.makeText(
                    context,
                    state.message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            is LoginStates.Loading -> isLoading = true
            LoginStates.Success -> {
                isLoading = false
                navController.navigate(HomeDestination)
            }

            null -> Unit
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(mediumPadding),
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LoginForm(
                modifier = Modifier.align(Alignment.Center),
            ) { email, password ->
                loginViewModel.tryLogin(email, password)
            }

            LoginFooter(
                modifier = Modifier.align(Alignment.BottomCenter),
            ) {
                navController.navigate(RegisterDestination)
            }
        }
    }
}

@Composable
private fun LoginForm(
    modifier: Modifier = Modifier,
    onclickLoginButton: (String, String) -> Unit,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    var inputEmail by remember { mutableStateOf("") }

    var inputPassword by remember { mutableStateOf("") }

    var hasErrorInputEmail by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyFormTextField(
            modifier = Modifier.fillMaxWidth(),
            hint = "Email",
            onChangeTextListener = { input ->
                inputEmail = input
                hasErrorInputEmail = !Patterns.EMAIL_ADDRESS.matcher(input).matches()
            },
            hasErrorInputText = hasErrorInputEmail,
            errorText = "Email invalido"
        )

        MyPasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            hint = "contraseña",
            trailingIconClick = {
                isPasswordVisible = !isPasswordVisible
            },
            isPasswordVisible = isPasswordVisible
        ) { input ->
            inputPassword = input
        }

        MyButton(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .padding(top = xLargePadding),
            isEnable = !hasErrorInputEmail && inputPassword.isNotEmpty() && inputEmail.isNotEmpty(),
            text = "Iniciar sesion"
        ) {
            onclickLoginButton(inputEmail, inputPassword)
        }
    }
}

@Composable
fun LoginFooter(modifier: Modifier, registerOnClick: () -> Unit) {
    Row(
        modifier = modifier
            .padding(xLargePadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.login_dont_have_account))
        TextButton(onClick = registerOnClick) {
            Text(text = stringResource(R.string.login_sing_up_hint))
        }
    }
}