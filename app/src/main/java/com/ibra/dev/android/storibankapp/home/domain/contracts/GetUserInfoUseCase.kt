package com.ibra.dev.android.storibankapp.home.domain.contracts

import com.ibra.dev.android.storibankapp.home.presentation.contracts.HomeScreenState

interface GetUserInfoUseCase {

    suspend fun invoke(email: String): HomeScreenState
}