package com.ibra.dev.android.storibankapp.core.data.contracts

import android.graphics.Bitmap

interface ImageStoreManager {

    suspend fun uploadImage(
        image: Bitmap?,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    )
}