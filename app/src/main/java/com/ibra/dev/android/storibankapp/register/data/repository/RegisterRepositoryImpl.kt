package com.ibra.dev.android.storibankapp.register.data.repository

import com.ibra.dev.android.storibankapp.core.data.contracts.UserRemoteDataSource
import com.ibra.dev.android.storibankapp.core.data.entities.UserResponse
import com.ibra.dev.android.storibankapp.login.domain.models.UserSingUpDto
import com.ibra.dev.android.storibankapp.login.domain.models.toUserEntity
import com.ibra.dev.android.storibankapp.register.data.contracts.RegisterRepository
import kotlinx.coroutines.flow.Flow

class RegisterRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource
) : RegisterRepository {
    override suspend fun registerUser(user: UserSingUpDto): Flow<UserResponse> {
       return userRemoteDataSource.createUser(user.toUserEntity())
    }
}
