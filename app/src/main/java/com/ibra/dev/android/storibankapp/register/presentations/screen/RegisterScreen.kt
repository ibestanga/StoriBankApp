package com.ibra.dev.android.storibankapp.register.presentations.screen

import android.content.Context
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
import com.ibra.dev.android.storibankapp.core.presentation.navigations.LoginDestination
import com.ibra.dev.android.storibankapp.core.presentation.navigations.RegisterDestination
import com.ibra.dev.android.storibankapp.core.presentation.widgets.CameraModalBottomSheet
import com.ibra.dev.android.storibankapp.core.presentation.widgets.MyButton
import com.ibra.dev.android.storibankapp.core.presentation.widgets.RequestCameraPermission
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

    var showBottomSheet by remember { mutableStateOf(false) }

    var pictureBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val context = LocalContext.current

    HandlerScreenState(
        stateScreen,
        context,
        onLoadingState = { isLoading = it },
        onSuccessNavigate = {
            navController.navigate(LoginDestination)
            navController.popBackStack<RegisterDestination>(inclusive = true)
        }
    )

    if (requestCameraPermission) {
        RequestCameraPermission { granted ->
            if (granted) {
                showBottomSheet = true
            } else {
                Toast.makeText(context, "Permiso denegado", Toast.LENGTH_SHORT).show()
            }
            requestCameraPermission = false
        }
    }

    if (showBottomSheet) {
        CameraModalBottomSheet(
            onBitmapCaptured = { bitmap ->
                registerViewModel.onPictureChange(bitmap)
                pictureBitmap = bitmap
                showBottomSheet = false
            }
        )
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
private fun HandlerScreenState(
    stateScreen: RegisterScreenStates,
    context: Context,
    onLoadingState: (Boolean) -> Unit,
    onSuccessNavigate: () -> Unit,
) {
    LaunchedEffect(key1 = stateScreen) {
        when (val state = stateScreen) {
            is RegisterScreenStates.Loading -> onLoadingState(true)
            is RegisterScreenStates.Success -> onSuccessNavigate()

            is RegisterScreenStates.Error -> {
                onLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }

            else -> Unit
        }
    }
}

