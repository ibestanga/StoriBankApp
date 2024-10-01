package com.ibra.dev.android.storibankapp.register.data.contracts

import com.ibra.dev.android.storibankapp.core.data.entities.UserResponse
import com.ibra.dev.android.storibankapp.login.domain.models.UserSingUpDto
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {

    suspend fun registerUser(user: UserSingUpDto): Flow<UserResponse>
}