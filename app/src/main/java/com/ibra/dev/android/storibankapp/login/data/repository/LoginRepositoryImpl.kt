package com.ibra.dev.android.storibankapp.login.data.repository

import com.ibra.dev.android.storibankapp.login.data.contracts.LoginRepository
import com.ibra.dev.android.storibankapp.login.data.contracts.LoginRemoteDataSource
import kotlinx.coroutines.flow.Flow

class LoginRepositoryImpl(
    private val remoteDataSource: LoginRemoteDataSource
) : LoginRepository {

    override suspend fun getCredentials(email: String): Flow<String> {
        return remoteDataSource.getCredentials(email)
    }
}