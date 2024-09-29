package com.ibra.dev.android.storibankapp.login.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibra.dev.android.storibankapp.login.domain.contracts.CanUserLoginUseCase
import com.ibra.dev.android.storibankapp.login.presentation.states.LoginStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: CanUserLoginUseCase
) : ViewModel() {

    private val _loginScreenEventsStateFlow = MutableStateFlow<LoginStates?>(null)
    val loginScreenEventsStateFlow: StateFlow<LoginStates?> get() = _loginScreenEventsStateFlow

    fun login(email: String, password: String) {
        _loginScreenEventsStateFlow.value = LoginStates.Loading
        viewModelScope.launch {
            _loginScreenEventsStateFlow.value = loginUseCase.invoke(email, password).also {
                Log.i(LoginViewModel::class.simpleName, "login: $it")
            }
        }
    }
}