package com.ibra.dev.android.storibankapp.login.domain.usecase

import android.util.Log
import com.ibra.dev.android.storibankapp.login.data.contracts.LoginRepository
import com.ibra.dev.android.storibankapp.login.domain.contracts.CanUserLoginUseCase
import com.ibra.dev.android.storibankapp.login.presentation.states.LoginStates
import kotlinx.coroutines.flow.catch

class CanUserLoginUseCaseImpl(
    private val repository: LoginRepository
) : CanUserLoginUseCase {

    override suspend fun invoke(email: String, password: String): LoginStates {
        var result: LoginStates = LoginStates.Error("An error occurred")
        repository.getCredentials(email).catch { e ->
            result = LoginStates.Error(e.message.orEmpty())
            Log.e(CanUserLoginUseCaseImpl::class.java.simpleName, "login error --> ${e.message}", e)
        }.collect {
            result = if (it == password) {
                LoginStates.Success
            } else {
                LoginStates.Error("Invalid credentials")
            }
        }
        return result
    }
}
