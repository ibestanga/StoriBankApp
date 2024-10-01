package com.ibra.dev.android.storibankapp.register.presentations.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.ibra.dev.android.storibankapp.core.presentation.widgets.MyFormTextField
import com.ibra.dev.android.storibankapp.core.presentation.widgets.MyPasswordTextField

@Composable
fun NameInput(
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
fun SurnameInput(
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
fun EmailInput(
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
fun PasswordInput(
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

