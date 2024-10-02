package com.ibra.dev.android.storibankapp.register.data.repository

import com.ibra.dev.android.storibankapp.core.data.contracts.ImageStoreManager
import com.ibra.dev.android.storibankapp.core.data.contracts.UserRemoteDataSource
import com.ibra.dev.android.storibankapp.core.data.entities.UserResponse
import com.ibra.dev.android.storibankapp.core.utils.orAlternative
import com.ibra.dev.android.storibankapp.login.domain.models.UserSingUpDto
import com.ibra.dev.android.storibankapp.login.domain.models.toUserEntity
import com.ibra.dev.android.storibankapp.register.data.contracts.RegisterRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RegisterRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val imageStoreManager: ImageStoreManager
) : RegisterRepository {
    override suspend fun registerUser(user: UserSingUpDto): Flow<UserResponse> {
        return try {
            val urlPictureDni: CompletableDeferred<String> = CompletableDeferred()
            coroutineScope {
                    imageStoreManager.uploadImageDni(
                        user.dniPicture,
                        { url -> urlPictureDni.complete(url) },
                        { exception -> throw exception }
                    )
            }
            val axu = urlPictureDni.await()
            userRemoteDataSource.createUser(user.toUserEntity().copy(urlDniPicture = axu))
        } catch (e: Exception) {
            flowOf(
                UserResponse(
                    isSuccess = false,
                    message = e.message.orAlternative("Unknown error occurred")
                )
            )
        }
    }
}
