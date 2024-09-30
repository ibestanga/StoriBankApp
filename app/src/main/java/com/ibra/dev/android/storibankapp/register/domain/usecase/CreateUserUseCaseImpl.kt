package com.ibra.dev.android.storibankapp.register.domain.usecase

import android.util.Log
import com.ibra.dev.android.storibankapp.core.data.entities.UserEntity
import com.ibra.dev.android.storibankapp.register.data.contracts.RegisterRepository
import com.ibra.dev.android.storibankapp.register.domain.contracts.CreateUserUseCase
import com.ibra.dev.android.storibankapp.register.presentations.states.RegisterScreenStates
import kotlinx.coroutines.flow.catch

class CreateUserUseCaseImpl(
    private val registerRepository: RegisterRepository
) : CreateUserUseCase {

    override suspend fun invoke(user: UserEntity): RegisterScreenStates {
        val result: RegisterScreenStates = RegisterScreenStates.Initial
        registerRepository.registerUser(user).catch { e ->
            Log.e(CreateUserUseCase::class.java.simpleName, "invoke: ", e)
            RegisterScreenStates.Error(e.message.orEmpty())
        }.collect { response ->
            if (response.isSuccess == true) {
                RegisterScreenStates.Success(response.message.orEmpty())
                Log.i(CreateUserUseCaseImpl::class.java.simpleName, "invoke: result: $response")
            } else {
                RegisterScreenStates.Error(response.message.orEmpty())
                Log.e(CreateUserUseCaseImpl::class.java.simpleName, "invoke: result: $response")
            }
        }
        return result
    }
}