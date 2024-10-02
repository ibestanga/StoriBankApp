package com.ibra.dev.android.storibankapp.home.di

import com.ibra.dev.android.storibankapp.core.data.contracts.UserRemoteDataSource
import com.ibra.dev.android.storibankapp.home.data.contracts.HomeRepository
import com.ibra.dev.android.storibankapp.home.data.repository.HomeRepositoryImpl
import com.ibra.dev.android.storibankapp.home.domain.contracts.GetUserInfoUseCase
import com.ibra.dev.android.storibankapp.home.domain.contracts.MapperUserEntityToDto
import com.ibra.dev.android.storibankapp.home.domain.usecase.GetUserInfoUseCaseImpl
import com.ibra.dev.android.storibankapp.home.domain.usecase.MapperUserEntityToDtoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class HomeComponent {

    @Provides
    fun provideHomeRepository(
        userDataSource: UserRemoteDataSource
    ): HomeRepository {
        return HomeRepositoryImpl(userDataSource)
    }

    @Provides
    fun provideGetUserInfoUseCase(
        repository: HomeRepository,
        mapper: MapperUserEntityToDto
    ): GetUserInfoUseCase {
        return GetUserInfoUseCaseImpl(repository, mapper)
    }

    @Provides
    fun provideMapperUserEntityToDto(): MapperUserEntityToDto {
        return MapperUserEntityToDtoImpl()
    }

}