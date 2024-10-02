package com.ibra.dev.android.storibankapp.register.presentations.viewmodels

import android.graphics.Bitmap
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibra.dev.android.storibankapp.register.domain.models.UserSingUpDto
import com.ibra.dev.android.storibankapp.register.domain.models.isValid
import com.ibra.dev.android.storibankapp.register.domain.contracts.CreateUserUseCase
import com.ibra.dev.android.storibankapp.register.presentations.states.RegisterScreenStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterScreenViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    private val _screenEventsStateFlow =
        MutableStateFlow<RegisterScreenStates>(RegisterScreenStates.Initial)
    val screenEventsStateFlow: StateFlow<RegisterScreenStates> get() = _screenEventsStateFlow

    private var userInfo = UserSingUpDto()

    private val _isValidNameInputStateFlow = MutableStateFlow(true)
    val isValidNameInputStateFlow: StateFlow<Boolean> get() = _isValidNameInputStateFlow

    private val _isValidSurnameInputStateFlow = MutableStateFlow(true)
    val isValidSurnameInputStateFlow: StateFlow<Boolean> get() = _isValidSurnameInputStateFlow

    private val _isValidEmailInputStateFlow = MutableStateFlow(true)
    val isValidEmailInputStateFlow: StateFlow<Boolean> get() = _isValidEmailInputStateFlow

    private val _isValidPasswordInputStateFlow = MutableStateFlow(true)
    val isValidPasswordInputStateFlow: StateFlow<Boolean> get() = _isValidPasswordInputStateFlow

    private val _isEnableButtonStateFlow = MutableStateFlow(false)
    val isEnableButtonStateFlow: StateFlow<Boolean> get() = _isEnableButtonStateFlow

    fun registerUser() {
        viewModelScope.launch {
            _screenEventsStateFlow.value = RegisterScreenStates.Loading
            _screenEventsStateFlow.value = withContext(Dispatchers.IO) {
                createUserUseCase.invoke(userInfo)
            }
        }
    }

    fun reInitState() {
        _screenEventsStateFlow.value = RegisterScreenStates.Initial
    }

    fun onNameChange(name: String) {
        _isValidNameInputStateFlow.value = isValidField(name, 3).also { isValid ->
            if (isValid) userInfo = userInfo.copy(name = name)
        }
        isEnableButton()
    }

    fun onSurnameChange(surname: String) {
        _isValidSurnameInputStateFlow.value = isValidField(surname, 3).also { isValid ->
            if (isValid) userInfo = userInfo.copy(surname = surname)
        }
        isEnableButton()
    }

    fun onEmailChange(email: String) {
        _isValidEmailInputStateFlow.value =
            Patterns.EMAIL_ADDRESS.matcher(email).matches().also { isValid ->
                if (isValid) userInfo = userInfo.copy(email = email)
            }
        isEnableButton()
    }

    fun onPasswordChange(password: String) {
        _isValidPasswordInputStateFlow.value = isValidField(password, 6).also { isValid ->
            if (isValid) userInfo = userInfo.copy(password = password)
        }
        isEnableButton()
    }

    fun onPictureChange(picture: Bitmap) {
        userInfo = userInfo.copy(dniPicture = picture)
        isEnableButton()
    }

    private fun isValidField(field: String, minLength: Int): Boolean {
        return field.length >= minLength
    }

    private fun isEnableButton() {
        _isEnableButtonStateFlow.value = userInfo.isValid()
    }
}