package com.ibra.dev.android.storibankapp.login.data.contracts

import kotlinx.coroutines.flow.Flow

interface LoginRemoteDataSource {

    suspend fun getCredentials(email: String): Flow<String>
}