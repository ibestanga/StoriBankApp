package com.ibra.dev.android.storibankapp.login.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibra.dev.android.storibankapp.login.domain.contracts.CanUserLoginUseCase
import com.ibra.dev.android.storibankapp.login.presentation.states.LoginStates
import com.ibra.dev.android.storibankapp.register.presentations.states.RegisterScreenStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: CanUserLoginUseCase
) : ViewModel() {

    private val _loginScreenEventsStateFlow = MutableStateFlow<LoginStates>(LoginStates.Init)
    val loginScreenEventsStateFlow: StateFlow<LoginStates> get() = _loginScreenEventsStateFlow

    fun tryLogin(email: String, password: String) {
        _loginScreenEventsStateFlow.value = LoginStates.Loading
        viewModelScope.launch {
            _loginScreenEventsStateFlow.value = withContext(Dispatchers.Default) {
                loginUseCase.invoke(email, password).also { result ->
                    Log.i(LoginViewModel::class.simpleName, "login: $result")
                }
            }
        }
    }

    fun clearState() {
        _loginScreenEventsStateFlow.value = LoginStates.Init
    }
}