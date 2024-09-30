package com.ibra.dev.android.storibankapp.login.data.repository


import com.ibra.dev.android.storibankapp.core.data.contracts.UserRemoteDataSource
import com.ibra.dev.android.storibankapp.core.data.entities.UserResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class LoginRepositoryImplTest {

    private fun providerUserRemoteDataSource(): UserRemoteDataSource {
        return mockk(relaxed = true)
    }

    private fun providerSut(remoteDataSource: UserRemoteDataSource): LoginRepositoryImpl {
        return LoginRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `when call getUserData should call remoteDataSource`() = runBlocking {
        // Given
        val remoteDataSource = providerUserRemoteDataSource()
        val sut = providerSut(remoteDataSource)
        val email = "ibra@gmail.com"

        // When
        sut.getUserData(email)

        // Then
        coVerify { remoteDataSource.getUser(email) }
    }

    @Test
    fun `when call getUserData should call remoteDataSource with correct email`() = runBlocking {
        // Given
        val remoteDataSource = providerUserRemoteDataSource()
        val sut = providerSut(remoteDataSource)
        val email = "ibra@gmail.com"

        // When
        sut.getUserData(email)

        // Then
        coVerify { remoteDataSource.getUser(email) }
    }

    @Test
    fun `when call getUserData then return UserResponse `() = runBlocking {
        // Given
        val remoteDataSource = providerUserRemoteDataSource()
        val sut = providerSut(remoteDataSource)
        val email = "ibra@gmail.com"
        val messageExpected = "test complete"

        coEvery { remoteDataSource.getUser(email) } returns flowOf(
            UserResponse(
                isSuccess = true,
                message = messageExpected,
                data = null
            )
        )

        // When
        val result = sut.getUserData(email)

        // Then
        assertEquals(messageExpected, result.first().message)
    }

}