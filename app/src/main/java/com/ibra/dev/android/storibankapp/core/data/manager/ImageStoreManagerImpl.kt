package com.ibra.dev.android.storibankapp.core.data.manager

import android.graphics.Bitmap
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.ibra.dev.android.storibankapp.core.data.contracts.ImageStoreManager
import java.io.ByteArrayOutputStream
import java.util.UUID

class ImageStoreManagerImpl(
    firebaseStorage: FirebaseStorage
) : ImageStoreManager {

    private val storageReference = firebaseStorage.reference

    override suspend fun uploadImage(
        image: Bitmap?,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val filename = UUID.randomUUID().toString()
        val imageRef: StorageReference = storageReference.child("images/$filename")

        if (image == null) {
            onFailure(IllegalStateException("Image is null"))
            return
        }

        imageRef.putBytes(bitmapToByteArray(image))
            .addOnSuccessListener { _ ->
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    onSuccess(uri.toString())
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

}