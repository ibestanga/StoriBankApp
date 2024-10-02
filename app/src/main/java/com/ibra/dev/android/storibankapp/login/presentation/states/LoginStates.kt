package com.ibra.dev.android.storibankapp.login.presentation.states

sealed class LoginStates {
    data object Init : LoginStates()
    data class Success(val email: String) : LoginStates()
    data class Error(val message: String) : LoginStates()
    data object Loading : LoginStates()
}