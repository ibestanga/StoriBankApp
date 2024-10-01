package com.ibra.dev.android.storibankapp.core.presentation.widgets

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun RequestCameraPermission(onListenerGrantedPermission: (Boolean) -> Unit) {
    val context = LocalContext.current

    val cameraPermissionGranted = remember {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        onListenerGrantedPermission(isGranted)
    }

    LaunchedEffect(Unit) {
        if (cameraPermissionGranted.not()) {
            launcher.launch(Manifest.permission.CAMERA)
        } else {
            onListenerGrantedPermission(true)
        }
    }
}