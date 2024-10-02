package com.ibra.dev.android.storibankapp.home.data.contracts

import com.ibra.dev.android.storibankapp.core.data.entities.UserResponse
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getUserInfo(email: String): Flow<UserResponse>
}