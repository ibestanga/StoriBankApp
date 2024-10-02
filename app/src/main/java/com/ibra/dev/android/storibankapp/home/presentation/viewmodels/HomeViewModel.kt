package com.ibra.dev.android.storibankapp.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibra.dev.android.storibankapp.home.domain.contracts.GetUserInfoUseCase
import com.ibra.dev.android.storibankapp.home.presentation.contracts.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    private val _homeEventsStateFlow = MutableStateFlow<HomeScreenState>(HomeScreenState.Initial)
    val homeEventsStateFlow: StateFlow<HomeScreenState> get() = _homeEventsStateFlow

    fun getUserInfo(email: String) {
        viewModelScope.launch {
            _homeEventsStateFlow.value = HomeScreenState.Loading
            _homeEventsStateFlow.value = withContext(Dispatchers.IO) {
                getUserInfoUseCase.invoke(
                    email
                )
            }
        }
    }
}