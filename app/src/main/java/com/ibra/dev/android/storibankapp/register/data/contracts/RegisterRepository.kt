package com.ibra.dev.android.storibankapp.register.data.contracts

import com.ibra.dev.android.storibankapp.core.data.entities.UserEntity
import com.ibra.dev.android.storibankapp.core.data.entities.UserResponse
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {

    suspend fun registerUser(user: UserEntity): Flow<UserResponse>
}