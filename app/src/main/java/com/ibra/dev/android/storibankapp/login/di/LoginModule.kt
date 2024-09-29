package com.ibra.dev.android.storibankapp.login.di

import com.google.firebase.firestore.FirebaseFirestore
import com.ibra.dev.android.storibankapp.login.data.contracts.LoginRepository
import com.ibra.dev.android.storibankapp.login.data.contracts.LoginRemoteDataSource
import com.ibra.dev.android.storibankapp.login.data.datasource.LoginRemoteDataSourceImpl
import com.ibra.dev.android.storibankapp.login.data.repository.LoginRepositoryImpl
import com.ibra.dev.android.storibankapp.login.domain.contracts.CanUserLoginUseCase
import com.ibra.dev.android.storibankapp.login.domain.usecase.CanUserLoginUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class LoginModule {

    @Provides
    fun provideLoginUseCase(repository: LoginRepository): CanUserLoginUseCase {
        return CanUserLoginUseCaseImpl(repository)
    }

    @Provides
    fun provideLoginRepository(remoteDataSource: LoginRemoteDataSource): LoginRepository {
        return LoginRepositoryImpl(remoteDataSource)
    }

    @Provides
    fun provideLoginRemoteDataSource(dataBase: FirebaseFirestore): LoginRemoteDataSource {
        return LoginRemoteDataSourceImpl(dataBase)
    }
}