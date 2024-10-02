package com.ibra.dev.android.storibankapp.home.presentation.contracts

import com.ibra.dev.android.storibankapp.home.domain.models.HomeUserDto

sealed class HomeScreenState {

    data object Initial : HomeScreenState()
    data object Loading : HomeScreenState()
    data class Error(val message: String) : HomeScreenState()
    data class Success(val data: HomeUserDto) : HomeScreenState()
}