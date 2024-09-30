package com.ibra.dev.android.storibankapp.core.presentation.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.ibra.dev.android.storibankapp.R
import com.ibra.dev.android.storibankapp.ui.theme.mainBlue
import com.ibra.dev.android.storibankapp.ui.theme.myGray

@Composable
private fun getButtonColor() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = mainBlue,
    unfocusedBorderColor = myGray
)

@Composable
fun MyFormTextField(
    modifier: Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    hint: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    errorText: String = stringResource(R.string.generic_error_text_field),
    hasErrorInputText: Boolean = false,
    onChangeTextListener: (String) -> Unit,
) {
    var text by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        modifier = modifier,
        value = text,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        onValueChange = { onChangeText ->
            text = onChangeText
            onChangeTextListener(onChangeText)
        },
        isError = hasErrorInputText,
        supportingText = {
            if (hasErrorInputText) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorText,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        placeholder = {
            Text(text = text)
        },
        label = {
            Text(
                text = hint,
            )
        },
        shape = RoundedCornerShape(size = 10.dp),
        colors = getButtonColor(),
    )
}

@Composable
fun MyPasswordTextField(
    modifier: Modifier,
    hint: String,
    isPasswordVisible: Boolean = false,
    errorText: String = "",
    isError: Boolean = false,
    trailingIconClick: (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    onChangeTextListener: (String) -> Unit,
) {
    var text by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        modifier = modifier,
        value = text,
        leadingIcon = leadingIcon,
        trailingIcon = {
            trailingIconClick?.let {
                IconPasswordField(
                    isPasswordVisible = isPasswordVisible,
                    onClick = it
                )
            }

        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        onValueChange = { onChangeText ->
            text = onChangeText
            onChangeTextListener(onChangeText)
        },
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorText,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        placeholder = {
            Text(text = text)
        },
        label = {
            Text(
                text = hint
            )
        },
        shape = RoundedCornerShape(size = 10.dp),
        colors = getButtonColor(),
    )
}

@Composable
fun IconPasswordField(isPasswordVisible: Boolean, onClick: () -> Unit) {
    IconButton(onClick = {
        onClick()
    }) {
        Image(
            painter = painterResource(
                id = if (isPasswordVisible) {
                    R.drawable.ic_show_password
                } else {
                    R.drawable.ic_hide_password
                }
            ),
            contentDescription = "password eye icon"
        )
    }
}