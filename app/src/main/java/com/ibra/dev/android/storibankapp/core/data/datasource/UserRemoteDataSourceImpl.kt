package com.ibra.dev.android.storibankapp.core.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.ibra.dev.android.storibankapp.core.data.contracts.UserRemoteDataSource
import com.ibra.dev.android.storibankapp.core.data.entities.UserEntity
import com.ibra.dev.android.storibankapp.core.data.entities.UserResponse

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await


class UserRemoteDataSourceImpl(
    private val database: FirebaseFirestore
) : UserRemoteDataSource {
    override suspend fun getUser(email: String): Flow<UserResponse> = flow {
        val snapshot = database.collection(CLIENTS_COLLECTION).document(email).get().await()
        if (snapshot.exists()) {
            snapshot.toObject(UserEntity::class.java)?.let {
                emit(UserResponse(isSuccess = true, message = "User found", data = it))
            }
        } else {
            emit(UserResponse(isSuccess = false, message = USER_NOT_FOUND))
        }
    }

    override suspend fun createUser(user: UserEntity): Flow<UserResponse> {
        return flow {
            var result = UserResponse()
            user.email?.let { email ->
                database.collection(CLIENTS_COLLECTION).document(email).set(user)
                    .addOnFailureListener { exception ->
                        result = result.copy(isSuccess = false, message = exception.message)
                    }.addOnSuccessListener {
                        result =
                            result.copy(isSuccess = true, message = "User created successfully")
                    }
            } ?: throw IllegalArgumentException("User email is null")
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