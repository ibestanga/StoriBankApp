package com.ibra.dev.android.storibankapp.register.presentations.screen.form

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.layout.Box
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
import com.ibra.dev.android.storibankapp.R
import com.ibra.dev.android.storibankapp.core.presentation.navigations.SingUpResultDestination
import com.ibra.dev.android.storibankapp.core.presentation.widgets.CameraModalBottomSheet
import com.ibra.dev.android.storibankapp.core.presentation.widgets.MyButton
import com.ibra.dev.android.storibankapp.core.presentation.widgets.RequestCameraPermission
import com.ibra.dev.android.storibankapp.register.presentations.screen.result.BobsitoState
import com.ibra.dev.android.storibankapp.register.presentations.states.RegisterScreenStates
import com.ibra.dev.android.storibankapp.register.presentations.viewmodels.RegisterScreenViewModel
import com.ibra.dev.android.storibankapp.ui.theme.mediumPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingUpScreen(navController: NavController) {

    val registerViewModel: RegisterScreenViewModel = hiltViewModel()

    val stateScreen by registerViewModel.screenEventsStateFlow.collectAsState()

    var requestCameraPermission by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(false) }

    var showCameraBottomSheet by remember { mutableStateOf(false) }

    var pictureBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val context = LocalContext.current

    HandlerScreenState(
        navController,
        stateScreen,
        onLoadingState = {
            isLoading = it
            registerViewModel.reInitState()
        }
    )

    if (requestCameraPermission) {
        RequestCameraPermission { granted ->
            if (granted) {
                showCameraBottomSheet = true
            } else {
                Toast.makeText(context, "Permiso denegado", Toast.LENGTH_SHORT).show()
            }
            requestCameraPermission = false
        }
    }

    CameraBottomDialog(showCameraBottomSheet) {
        pictureBitmap = it
        showCameraBottomSheet = false
        registerViewModel.onPictureChange(it)
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
                SingUpForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    viewModel = registerViewModel,
                    navCameraAction = {
                        requestCameraPermission = true
                    },
                    pictureBitmap = pictureBitmap
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
private fun CameraBottomDialog(
    showBottomSheet: Boolean,
    onBitmapResult: (Bitmap) -> Unit,
) {
    if (showBottomSheet) {
        CameraModalBottomSheet(
            onBitmapCaptured = { bitmap ->
                onBitmapResult(bitmap)
            }
        )
    }
}

@Composable
private fun HandlerScreenState(
    navController: NavController,
    stateScreen: RegisterScreenStates,
    onLoadingState: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = stateScreen) {
        when (stateScreen) {
            is RegisterScreenStates.Loading -> onLoadingState(true)
            is RegisterScreenStates.Success -> {
                onLoadingState(true)
                navController.navigate(
                    SingUpResultDestination(
                        msg = context.getString(R.string.tu_cuenta_ha_sido_creada_exitosamente),
                        state = BobsitoState.HAPPY
                    )
                )
            }

            is RegisterScreenStates.Error -> {
                onLoadingState(false)
                navController.navigate(
                    SingUpResultDestination(
                        msg = stateScreen.message,
                        state = BobsitoState.SAD
                    )
                )
            }

            else -> Unit
        }
    }
}

