package com.ibra.dev.android.storibankapp.core.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.ibra.dev.android.storibankapp.core.data.contracts.ImageStoreManager
import com.ibra.dev.android.storibankapp.core.data.contracts.UserRemoteDataSource
import com.ibra.dev.android.storibankapp.core.data.datasource.UserRemoteDataSourceImpl
import com.ibra.dev.android.storibankapp.core.data.manager.ImageStoreManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(
        firestore: FirebaseFirestore
    ): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return Firebase.storage
    }

    @Provides
    @Singleton
    fun provideImageStoreManager(firebaseStorage: FirebaseStorage): ImageStoreManager {
        return ImageStoreManagerImpl(firebaseStorage)
    }

}