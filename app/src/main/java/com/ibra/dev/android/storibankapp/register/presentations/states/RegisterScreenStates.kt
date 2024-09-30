package com.ibra.dev.android.storibankapp.register.presentations.states

sealed class RegisterScreenStates {
    data object Initial : RegisterScreenStates()
    data object Loading : RegisterScreenStates()
    data class Success(val message: String) : RegisterScreenStates()
    data class Error(val message: String) : RegisterScreenStates()
}