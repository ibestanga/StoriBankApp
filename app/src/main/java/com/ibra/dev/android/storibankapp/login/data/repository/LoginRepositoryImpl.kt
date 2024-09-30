package com.ibra.dev.android.storibankapp.login.data.repository

import com.ibra.dev.android.storibankapp.core.data.contracts.UserRemoteDataSource
import com.ibra.dev.android.storibankapp.core.data.entities.UserResponse
import com.ibra.dev.android.storibankapp.login.data.contracts.LoginRepository

import kotlinx.coroutines.flow.Flow

class LoginRepositoryImpl(
    private val remoteDataSource: UserRemoteDataSource
) : LoginRepository {

    override suspend fun getUserData(email: String): Flow<UserResponse> {
        return remoteDataSource.getUser(email)
    }
}