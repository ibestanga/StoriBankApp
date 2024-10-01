package com.ibra.dev.android.storibankapp.register.presentations.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ibra.dev.android.storibankapp.core.presentation.navigations.LoginDestination
import com.ibra.dev.android.storibankapp.core.presentation.navigations.RegisterDestination
import com.ibra.dev.android.storibankapp.core.presentation.widgets.MyButton
import com.ibra.dev.android.storibankapp.core.presentation.widgets.MyFormTextField
import com.ibra.dev.android.storibankapp.core.presentation.widgets.MyPasswordTextField
import com.ibra.dev.android.storibankapp.register.presentations.states.RegisterScreenStates
import com.ibra.dev.android.storibankapp.register.presentations.viewmodels.RegisterScreenViewModel
import com.ibra.dev.android.storibankapp.ui.theme.mediumPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingUpScreen(navController: NavController) {

    val registerViewModel: RegisterScreenViewModel = hiltViewModel()

    val stateScreen by registerViewModel.screenEventsStateFlow.collectAsState()

    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(key1 = stateScreen) {
        when (val state = stateScreen) {
            is RegisterScreenStates.Loading -> isLoading = true
            is RegisterScreenStates.Success -> {
                navController.navigate(LoginDestination)
                navController.popBackStack<RegisterDestination>(inclusive = true)
            }

            is RegisterScreenStates.Error -> {
                isLoading = false
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }

            else -> Unit
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Registro")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                RegisterForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    viewModel = registerViewModel
                )
                MyButton(
                    modifier = Modifier
                        .padding(mediumPadding)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    text = "Registrar",
                    isEnable = registerViewModel.isEnableButtonStateFlow.collectAsState().value
                ) {
                    registerViewModel.registerUser()
                }
            }
        }
    }
}

@Composable
private fun RegisterForm(
    modifier: Modifier,
    viewModel: RegisterScreenViewModel,
) {
    Column(modifier = modifier) {
        NameInput(
            modifier = Modifier
                .padding(mediumPadding)
                .fillMaxWidth(),
            isValid = viewModel.isValidNameInputStateFlow.collectAsState().value
        ) { input ->
            viewModel.onNameChange(input)
        }
        SurnameInput(
            modifier = Modifier
                .padding(mediumPadding)
                .fillMaxWidth(),
            isValid = viewModel.isValidSurnameInputStateFlow.collectAsState().value
        ) { input ->
            viewModel.onSurnameChange(input)
        }
        EmailInput(
            modifier = Modifier
                .padding(mediumPadding)
                .fillMaxWidth(),
            isValid = viewModel.isValidEmailInputStateFlow.collectAsState().value
        ) { input ->
            viewModel.onEmailChange(input)
        }
        PasswordInput(
            modifier = Modifier
                .padding(mediumPadding)
                .fillMaxWidth(),
            isValid = viewModel.isValidPasswordInputStateFlow.collectAsState().value
        ) { input ->
            viewModel.onPasswordChange(input)
        }
    }
}

@Composable
private fun NameInput(
    modifier: Modifier,
    isValid: Boolean = false,
    onNameChange: (String) -> Unit,
) {

    MyFormTextField(
        modifier = modifier,
        hint = "Nombre",
        errorText = "El nombre no puede estar vacio",
        isValidInput = isValid,
        onChangeTextListener = onNameChange,
    )
}

@Composable
private fun SurnameInput(
    modifier: Modifier,
    isValid: Boolean = false,
    onInputChange: (String) -> Unit,
) {

    MyFormTextField(
        modifier = modifier,
        hint = "Apellido",
        onChangeTextListener = onInputChange,
        errorText = "El apellido no puede estar vacio",
        isValidInput = isValid
    )
}

@Composable
private fun EmailInput(
    modifier: Modifier,
    isValid: Boolean = false,
    onInputChange: (String) -> Unit
) {
    MyFormTextField(
        modifier = modifier,
        hint = "Correo",
        onChangeTextListener = onInputChange,
        errorText = "El correo no es valido",
        isValidInput = isValid
    )
}

@Composable
private fun PasswordInput(
    modifier: Modifier,
    isValid: Boolean = false,
    onInputChange: (String) -> Unit
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    MyPasswordTextField(
        modifier = modifier,
        hint = "Contraseña",
        isPasswordVisible = isPasswordVisible,
        trailingIconClick = {
            isPasswordVisible = !isPasswordVisible
        },
        onChangeTextListener = onInputChange,
        isValidInput = isValid
    )
}

