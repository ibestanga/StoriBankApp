package com.ibra.dev.android.storibankapp.register.di

import com.ibra.dev.android.storibankapp.core.data.contracts.UserRemoteDataSource
import com.ibra.dev.android.storibankapp.register.data.contracts.RegisterRepository
import com.ibra.dev.android.storibankapp.register.data.repository.RegisterRepositoryImpl
import com.ibra.dev.android.storibankapp.register.domain.contracts.CreateUserUseCase
import com.ibra.dev.android.storibankapp.register.domain.usecase.CreateUserUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RegisterModule {

    @Provides
    fun provideRegisterUseCase(registerRepository: RegisterRepository): CreateUserUseCase {
        return CreateUserUseCaseImpl(registerRepository)
    }

    @Provides
    fun provideRegisterRepository(userRemoteDataSource: UserRemoteDataSource): RegisterRepository {
        return RegisterRepositoryImpl(userRemoteDataSource)
    }


}