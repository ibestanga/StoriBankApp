package com.ibra.dev.android.storibankapp.login.domain.contracts

import com.ibra.dev.android.storibankapp.login.presentation.states.LoginStates

interface CanUserLoginUseCase {

    suspend fun invoke(email: String, password: String): LoginStates
}