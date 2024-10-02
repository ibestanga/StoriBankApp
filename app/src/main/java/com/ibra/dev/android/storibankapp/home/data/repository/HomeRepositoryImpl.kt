package com.ibra.dev.android.storibankapp.home.data.repository

import com.ibra.dev.android.storibankapp.core.data.contracts.UserRemoteDataSource
import com.ibra.dev.android.storibankapp.core.data.entities.UserResponse
import com.ibra.dev.android.storibankapp.home.data.contracts.HomeRepository
import kotlinx.coroutines.flow.Flow

class HomeRepositoryImpl(
    private val userDataSource: UserRemoteDataSource,
): HomeRepository {

    override suspend fun getUserInfo(email: String): Flow<UserResponse> {
        return userDataSource.getUser(email)
    }
}