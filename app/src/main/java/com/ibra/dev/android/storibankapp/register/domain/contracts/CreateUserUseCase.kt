package com.ibra.dev.android.storibankapp.register.domain.contracts

import com.ibra.dev.android.storibankapp.register.domain.models.UserSingUpDto
import com.ibra.dev.android.storibankapp.register.presentations.states.RegisterScreenStates

interface CreateUserUseCase {

    suspend fun invoke(user: UserSingUpDto): RegisterScreenStates
}