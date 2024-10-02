package com.ibra.dev.android.storibankapp.register.data.repository

import com.ibra.dev.android.storibankapp.core.data.contracts.ImageStoreManager
import com.ibra.dev.android.storibankapp.core.data.contracts.UserRemoteDataSource
import com.ibra.dev.android.storibankapp.core.data.entities.UserEntity
import com.ibra.dev.android.storibankapp.core.data.entities.UserResponse
import com.ibra.dev.android.storibankapp.login.domain.models.UserSingUpDto
import com.ibra.dev.android.storibankapp.register.data.contracts.RegisterRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class RegisterRepositoryImplTest {

    private fun providerUserDataSource(): UserRemoteDataSource = mockk(relaxed = true)

    private fun providerImageStoreManager() = mockk<ImageStoreManager>(relaxed = true)

    private fun providerSut(
        dataSource: UserRemoteDataSource,
        imageStoreManager: ImageStoreManager
    ): RegisterRepository {
        return RegisterRepositoryImpl(dataSource, imageStoreManager)
    }

    @Test
    fun `when call registerUser should call userRemoteDataSource registerUser`() = runTest {
        // given
        val dataSource = providerUserDataSource()
        val imageStoreManager = providerImageStoreManager()
        val sut = providerSut(dataSource, imageStoreManager)
        val userEntity = UserSingUpDto("test", "test")

        // when
        sut.registerUser(userEntity)

        // then
        coVerify { dataSource.createUser(any()) }
    }

    @Test
    fun `when call registerUser and dataSource return flow with response should return response`() =
        runTest() {
            // given
            val dataSource = providerUserDataSource()
            val imageStoreManager = providerImageStoreManager()
            val sut = providerSut(dataSource, imageStoreManager)
            val userEntity = UserSingUpDto("test", "test")
            val messageExpected = "test"

            coEvery { dataSource.createUser(any()) } returns flowOf(
                UserResponse(
                    true,
                    messageExpected
                )
            )

            // when
            val result = sut.registerUser(userEntity)
            assertEquals(messageExpected, result.first().message)
        }
}