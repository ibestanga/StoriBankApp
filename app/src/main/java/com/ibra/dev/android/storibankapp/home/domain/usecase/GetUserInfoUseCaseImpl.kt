package com.ibra.dev.android.storibankapp.home.domain.usecase

import com.ibra.dev.android.storibankapp.home.data.contracts.HomeRepository
import com.ibra.dev.android.storibankapp.home.domain.contracts.GetUserInfoUseCase
import com.ibra.dev.android.storibankapp.home.domain.contracts.MapperUserEntityToDto
import com.ibra.dev.android.storibankapp.home.presentation.contracts.HomeScreenState

class GetUserInfoUseCaseImpl(
    private val repository: HomeRepository,
    private val mapper: MapperUserEntityToDto
) : GetUserInfoUseCase {

    override suspend fun invoke(email: String): HomeScreenState {
        var result: HomeScreenState = HomeScreenState.Initial
        repository.getUserInfo(email).collect { response ->
            result = if (response.isSuccess == true) {
                HomeScreenState.Success(mapper.invoke(response.data))
            } else {
                HomeScreenState.Error(response.message.orEmpty())
            }
        }
        return result
    }
}