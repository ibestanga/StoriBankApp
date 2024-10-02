package com.ibra.dev.android.storibankapp.register.data.repository

import android.graphics.Bitmap
import com.ibra.dev.android.storibankapp.core.data.contracts.ImageStoreManager
import com.ibra.dev.android.storibankapp.core.data.contracts.UserRemoteDataSource
import com.ibra.dev.android.storibankapp.core.data.entities.UserResponse
import com.ibra.dev.android.storibankapp.core.utils.orAlternative
import com.ibra.dev.android.storibankapp.login.domain.models.UserSingUpDto
import com.ibra.dev.android.storibankapp.login.domain.models.toUserEntity
import com.ibra.dev.android.storibankapp.register.data.contracts.RegisterRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RegisterRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val imageStoreManager: ImageStoreManager
) : RegisterRepository {
    override suspend fun registerUser(user: UserSingUpDto): Flow<UserResponse> {
        return try {
            val imageUrl = user.dniPicture?.let { uploadImageDni(it) }
            userRemoteDataSource.createUser(user.toUserEntity().copy(urlDniPicture = imageUrl))
        } catch (e: Exception) {
            flowOf(
                UserResponse(
                    isSuccess = false,
                    message = e.message.orAlternative("Unknown error occurred")
                )
            )
        }
    }

    private suspend fun uploadImageDni(dniPicture: Bitmap): String {
        val urlDeferred: CompletableDeferred<String> = CompletableDeferred()
        coroutineScope {
            imageStoreManager.uploadImageDni(
                dniPicture,
                { url -> urlDeferred.complete(url) },
                { exception -> throw exception }
            )
        }
        return urlDeferred.await()
    }
}
