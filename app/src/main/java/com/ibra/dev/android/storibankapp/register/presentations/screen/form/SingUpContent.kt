package com.ibra.dev.android.storibankapp.register.presentations.screen.form

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ibra.dev.android.storibankapp.R
import com.ibra.dev.android.storibankapp.register.presentations.viewmodels.RegisterScreenViewModel
import com.ibra.dev.android.storibankapp.ui.theme.mediumPadding
import com.ibra.dev.android.storibankapp.ui.theme.xLargePadding


@Composable
fun SingUpForm(
    modifier: Modifier,
    viewModel: RegisterScreenViewModel,
    navCameraAction: () -> Unit,
    pictureBitmap: Bitmap?
) {
    Column(modifier = modifier) {
        InputForm(
            modifier = Modifier
                .padding(mediumPadding)
                .fillMaxWidth(),
            hint = stringResource(R.string.name_copy),
            errorText = stringResource(R.string.error_text_field_at_least_three_characters),
            isValid = viewModel.isValidNameInputStateFlow.collectAsState().value
        ) { input ->
            viewModel.onNameChange(input)
        }
        InputForm(
            modifier = Modifier
                .padding(mediumPadding)
                .fillMaxWidth(),
            hint = stringResource(R.string.surname_copy),
            errorText = stringResource(R.string.error_text_field_at_least_three_characters),
            isValid = viewModel.isValidSurnameInputStateFlow.collectAsState().value
        ) { input ->
            viewModel.onSurnameChange(input)
        }
        InputForm(
            modifier = Modifier
                .padding(mediumPadding)
                .fillMaxWidth(),
            hint = stringResource(R.string.email_copy),
            errorText = stringResource(R.string.error_text_field_email_invalid),
            keyboardType = KeyboardType.Email,
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

        Card(
            modifier = Modifier
                .padding(start = xLargePadding, end = xLargePadding)
                .fillMaxWidth()
                .clickable {
                    navCameraAction()
                },
            shape = RoundedCornerShape(
                8.dp
            )
        ) {
            Box {
                pictureBitmap?.let { bitmap ->
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .align(Alignment.Center),
                        contentScale = ContentScale.Crop,
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = stringResource(id = R.string.generic_content_descriptions),
                    )
                } ?: Image(
                    painter = painterResource(id = R.drawable.outline_photo_camera_24),
                    contentDescription = stringResource(id = R.string.generic_content_descriptions),
                    modifier = Modifier
                        .padding(mediumPadding)
                        .fillMaxWidth()
                        .height(150.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}