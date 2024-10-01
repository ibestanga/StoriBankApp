package com.ibra.dev.android.storibankapp.register.presentations.screen

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
                        contentDescription = "Captured image",
                    )
                } ?: Image(
                    painter = painterResource(id = R.drawable.outline_photo_camera_24),
                    contentDescription = "Add a photo",
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