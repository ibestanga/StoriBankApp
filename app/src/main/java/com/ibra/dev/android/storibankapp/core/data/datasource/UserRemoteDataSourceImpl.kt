package com.ibra.dev.android.storibankapp.core.data.datasource

import android.database.sqlite.SQLiteBlobTooBigException
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.ibra.dev.android.storibankapp.core.data.contracts.ImageStoreManager
import com.ibra.dev.android.storibankapp.core.data.contracts.UserRemoteDataSource
import com.ibra.dev.android.storibankapp.core.data.entities.UserEntity
import com.ibra.dev.android.storibankapp.core.data.entities.UserResponse
import com.ibra.dev.android.storibankapp.core.utils.orAlternative
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class UserRemoteDataSourceImpl(
    private val firestore: FirebaseFirestore
) : UserRemoteDataSource {
    override suspend fun getUser(email: String): Flow<UserResponse> = flow {
        val snapshot = firestore.collection(CLIENTS_COLLECTION).document(email).get().await()
        if (snapshot.exists()) {
            snapshot.toObject(UserEntity::class.java)?.let {
                emit(UserResponse(isSuccess = true, message = "User found", data = it))
            }
        } else {
            emit(UserResponse(isSuccess = false, message = USER_NOT_FOUND))
        }
    }

    override suspend fun createUser(user: UserEntity): Flow<UserResponse> = flow {

        if (user.email.isNullOrEmpty()) {
            emit(UserResponse(isSuccess = false, message = "Email is required"))
            return@flow
        }

        try {
            Log.i("UserRemoteDataSourceImpl", "createUser: user: $user")
            firestore.collection(CLIENTS_COLLECTION).document(user.email).set(user).await()
            emit(UserResponse(isSuccess = true, message = "User created successfully"))
        } catch (e: Exception) {
            Log.e(this::class.java.simpleName, "createUser: ${e.message}", e)
            emit(
                UserResponse(
                    isSuccess = false,
                    message = e.message.orAlternative("Unknown error occurred")
                )
            )
        } catch (e: SQLiteBlobTooBigException) {
            Log.e(this::class.java.simpleName, "createUser: ${e.message}", e)
            emit(
                UserResponse(
                    isSuccess = false,
                    message = e.message.orAlternative("Unknown error occurred")
                )
            )
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