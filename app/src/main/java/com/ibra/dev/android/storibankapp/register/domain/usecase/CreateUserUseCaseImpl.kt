package com.ibra.dev.android.storibankapp.register.domain.usecase

import com.ibra.dev.android.storibankapp.register.domain.models.UserSingUpDto
import com.ibra.dev.android.storibankapp.register.data.contracts.RegisterRepository
import com.ibra.dev.android.storibankapp.register.domain.contracts.CreateUserUseCase
import com.ibra.dev.android.storibankapp.register.presentations.states.RegisterScreenStates

class CreateUserUseCaseImpl(
    private val registerRepository: RegisterRepository
) : CreateUserUseCase {

    override suspend fun invoke(user: UserSingUpDto): RegisterScreenStates {
        var result: RegisterScreenStates = RegisterScreenStates.Initial
        registerRepository.registerUser(user).collect { response ->
            result = if (response.isSuccess == true) {
                RegisterScreenStates.Success(response.message.orEmpty())
            } else {
                RegisterScreenStates.Error(response.message.orEmpty())
            }
        }
        return result
    }
}