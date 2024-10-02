package com.ibra.dev.android.storibankapp.core.data.contracts

import com.ibra.dev.android.storibankapp.core.data.entities.UserEntity
import com.ibra.dev.android.storibankapp.core.data.entities.UserResponse
import com.ibra.dev.android.storibankapp.register.domain.models.UserSingUpDto
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource {

    suspend fun getUser(email: String): Flow<UserResponse>

    suspend fun createUser(user: UserSingUpDto): Flow<UserResponse>

    suspend fun updateUser(user: UserEntity): Flow<UserResponse>
}