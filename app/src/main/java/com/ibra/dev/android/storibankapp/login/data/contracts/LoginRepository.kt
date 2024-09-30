package com.ibra.dev.android.storibankapp.login.data.contracts

import com.ibra.dev.android.storibankapp.core.data.entities.UserResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun getCredentials(email: String): Flow<UserResponse>
}