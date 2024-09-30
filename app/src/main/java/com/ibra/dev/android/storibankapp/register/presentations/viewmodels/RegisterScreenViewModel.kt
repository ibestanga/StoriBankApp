package com.ibra.dev.android.storibankapp.register.presentations.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibra.dev.android.storibankapp.core.data.entities.MovementsEntity
import com.ibra.dev.android.storibankapp.core.data.entities.UserEntity
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
    val screenEventsStateFlow: StateFlow<RegisterScreenStates?> get() = _screenEventsStateFlow

    fun registerUser() {
        viewModelScope.launch {
            _screenEventsStateFlow.value = RegisterScreenStates.Loading
            _screenEventsStateFlow.value = withContext(Dispatchers.IO) {
                createUserUseCase.invoke(
                    UserEntity(
                        name = "ibrahim ismael",
                        surname = "estanga",
                        email = "test@test",
                        password = "123456789",
                        dniPicture = "image base 64",
                        balance = 500.50,
                        movements = listOf(
                            MovementsEntity(
                                type = "deposit",
                                amount = 500.50
                            ),
                            MovementsEntity(
                                type = "withdraw",
                                amount = 100.50
                            )
                        )
                    )
                )
            }
        }
    }
}