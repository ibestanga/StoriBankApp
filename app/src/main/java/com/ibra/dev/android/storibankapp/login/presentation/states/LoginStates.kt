package com.ibra.dev.android.storibankapp.login.presentation.states

sealed class LoginStates {
    data object Success : LoginStates()
    data class Error(val message: String) : LoginStates()
    data object Loading : LoginStates()
}