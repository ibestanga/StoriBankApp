package com.ibra.dev.android.storibankapp.core.data.datasource

import android.graphics.Bitmap
import com.google.firebase.firestore.FirebaseFirestore
import com.ibra.dev.android.storibankapp.core.data.contracts.ImageStoreManager
import com.ibra.dev.android.storibankapp.core.data.contracts.UserRemoteDataSource
import com.ibra.dev.android.storibankapp.core.data.entities.UserEntity
import com.ibra.dev.android.storibankapp.core.data.entities.UserResponse
import com.ibra.dev.android.storibankapp.core.utils.orAlternative
import com.ibra.dev.android.storibankapp.register.domain.models.UserSingUpDto
import com.ibra.dev.android.storibankapp.register.domain.models.toUserEntity
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRemoteDataSourceImpl(
    private val firestore: FirebaseFirestore,
    private val imageStoreManager: ImageStoreManager
) : UserRemoteDataSource {
    override suspend fun getUser(email: String): Flow<UserResponse> = flow {
        val responseDeferred: CompletableDeferred<UserResponse> = CompletableDeferred()
        firestore.collection(CLIENTS_COLLECTION).document(email).get()
            .addOnSuccessListener { snapshot ->
                try {
                    if (snapshot.exists()) {
                        snapshot.toObject(UserEntity::class.java)?.let {
                            responseDeferred.complete(
                                UserResponse(
                                    isSuccess = true,
                                    message = USER_FOUND,
                                    data = it
                                )
                            )
                        }
                    } else {
                        responseDeferred.complete(
                            UserResponse(
                                isSuccess = false,
                                message = USER_NOT_FOUND
                            )
                        )
                    }
                } catch (e: Exception) {
                    responseDeferred.complete(
                        UserResponse(
                            isSuccess = false,
                            message = e.message.orAlternative(UNKNOWN_ERROR_OCCURRED)
                        )
                    )
                }
            }.addOnFailureListener { exception ->
                responseDeferred.complete(
                    UserResponse(
                        isSuccess = false,
                        message = exception.message.orAlternative(UNKNOWN_ERROR_OCCURRED)
                    )
                )
            }
        emit(responseDeferred.await())
    }

    override suspend fun createUser(user: UserSingUpDto): Flow<UserResponse> = flow {
        if (emailExists(user.email).not()) {
            val imageUrl = user.dniPicture?.let { uploadImageDni(it) }.orEmpty()
            emit(uploadNewUser(user.toUserEntity().copy(urlDniPicture = imageUrl)))
        } else {
            emit(
                UserResponse(
                    isSuccess = false,
                    message = USER_ALREADY_EXISTS
                )
            )
        }
    }

    private suspend fun emailExists(email: String): Boolean {
        val responseDeferred: CompletableDeferred<Boolean> = CompletableDeferred()
        firestore.collection(CLIENTS_COLLECTION).document(email).get()
            .addOnSuccessListener { snapshot ->
                responseDeferred.complete(snapshot.exists())
            }.addOnFailureListener { _ ->
                responseDeferred.complete(false)
            }
        return responseDeferred.await()
    }

    private suspend fun uploadNewUser(user: UserEntity): UserResponse {
        return user.email?.let {
            val responseDeferred: CompletableDeferred<UserResponse> = CompletableDeferred()
            firestore.collection(CLIENTS_COLLECTION).document(it).set(user)
                .addOnSuccessListener {
                    responseDeferred.complete(
                        UserResponse(
                            isSuccess = true,
                            message = USER_CREATED,
                            data = user
                        )
                    )
                }.addOnFailureListener { exception ->
                    responseDeferred.complete(
                        UserResponse(
                            isSuccess = false,
                            message = exception.message.orAlternative(UNKNOWN_ERROR_OCCURRED)
                        )
                    )
                }
            responseDeferred.await()
        } ?: UserResponse(
            isSuccess = false,
            message = USER_EMAIL_IS_NULL
        )
    }


    private suspend fun uploadImageDni(dniPicture: Bitmap): String {
        val urlDeferred: CompletableDeferred<String> = CompletableDeferred()
        coroutineScope {
            imageStoreManager.uploadImage(
                dniPicture,
                { url -> urlDeferred.complete(url) },
                { exception -> throw exception }
            )
        }
        return urlDeferred.await()
    }

    override suspend fun updateUser(user: UserEntity): Flow<UserResponse> {
        TODO("Not yet implemented")
    }

    companion object {
        private const val USER_NOT_FOUND = "User not found"
        private const val CLIENTS_COLLECTION = "clients"
        private const val UNKNOWN_ERROR_OCCURRED = "Unknown error occurred"
        private const val USER_ALREADY_EXISTS = "User already exists"
        private const val USER_FOUND = "User found"
        private const val USER_CREATED = "User created"
        private const val USER_EMAIL_IS_NULL = "User email is null"
    }
}