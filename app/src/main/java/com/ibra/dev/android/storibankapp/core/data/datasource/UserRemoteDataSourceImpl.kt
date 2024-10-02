package com.ibra.dev.android.storibankapp.core.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.ibra.dev.android.storibankapp.core.data.contracts.UserRemoteDataSource
import com.ibra.dev.android.storibankapp.core.data.entities.UserEntity
import com.ibra.dev.android.storibankapp.core.data.entities.UserResponse
import com.ibra.dev.android.storibankapp.core.utils.orAlternative
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class UserRemoteDataSourceImpl(
    private val firestore: FirebaseFirestore
) : UserRemoteDataSource {
    override suspend fun getUser(email: String): Flow<UserResponse> = flow {
        val responseDeferred: CompletableDeferred<UserResponse> = CompletableDeferred()
        firestore.collection(CLIENTS_COLLECTION).document(email).get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    snapshot.toObject(UserEntity::class.java)?.let {
                        responseDeferred.complete(
                            UserResponse(
                                isSuccess = true,
                                message = "User found",
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
            }.addOnFailureListener { exception ->
                responseDeferred.complete(
                    UserResponse(
                        isSuccess = false,
                        message = exception.message.orAlternative("Unknown error occurred")
                    )
                )
            }
        emit(responseDeferred.await())
    }

    override suspend fun createUser(user: UserEntity): Flow<UserResponse> = flow {
        user.email?.let {
            val responseDeferred: CompletableDeferred<UserResponse> = CompletableDeferred()
            firestore.collection(CLIENTS_COLLECTION).document(it).set(user)
                .addOnSuccessListener {
                    responseDeferred.complete(
                        UserResponse(
                            isSuccess = true,
                            message = "User created",
                            data = user
                        )
                    )
                }.addOnFailureListener { exception ->
                    responseDeferred.complete(
                        UserResponse(
                            isSuccess = false,
                            message = exception.message.orAlternative("Unknown error occurred")
                        )
                    )
                }
            emit(responseDeferred.await())
        }
    }

    override suspend fun updateUser(user: UserEntity): Flow<UserResponse> {
        TODO("Not yet implemented")
    }

    companion object {
        private const val USER_NOT_FOUND = "User not found"
        private const val CLIENTS_COLLECTION = "clients"
    }
}