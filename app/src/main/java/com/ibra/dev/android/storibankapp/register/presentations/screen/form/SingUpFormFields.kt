package com.ibra.dev.android.storibankapp.register.presentations.screen.form

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.ibra.dev.android.storibankapp.R
import com.ibra.dev.android.storibankapp.core.presentation.widgets.MyFormTextField
import com.ibra.dev.android.storibankapp.core.presentation.widgets.MyPasswordTextField

@Composable
fun InputForm(
    modifier: Modifier,
    hint: String,
    errorText: String,
    isValid: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    onInputChange: (String) -> Unit
) {
    MyFormTextField(
        modifier = modifier,
        hint =hint,
        errorText = errorText,
        isValidInput = isValid,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        onChangeTextListener = onInputChange,
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
        hint = stringResource(R.string.password_copy),
        isPasswordVisible = isPasswordVisible,
        trailingIconClick = {
            isPasswordVisible = !isPasswordVisible
        },
        onChangeTextListener = onInputChange,
        errorText = stringResource(R.string.error_text_field_at_least_six_characters),
        isValidInput = isValid
    )
}
