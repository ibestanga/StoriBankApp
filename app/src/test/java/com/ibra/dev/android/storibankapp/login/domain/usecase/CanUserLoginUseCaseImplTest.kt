package com.ibra.dev.android.storibankapp.login.domain.usecase

import com.ibra.dev.android.storibankapp.core.data.entities.UserEntity
import com.ibra.dev.android.storibankapp.core.data.entities.UserResponse
import com.ibra.dev.android.storibankapp.login.data.contracts.LoginRepository
import com.ibra.dev.android.storibankapp.login.domain.contracts.CanUserLoginUseCase
import com.ibra.dev.android.storibankapp.login.presentation.states.LoginStates
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class CanUserLoginUseCaseImplTest {
    private fun providerLoginRepository(): LoginRepository = mockk(relaxed = true)

    private fun providerSut(repository: LoginRepository): CanUserLoginUseCase {
        return CanUserLoginUseCaseImpl(repository)
    }

    @Test
    fun `when call login and repository return response success  should return State Success`() =
        runBlocking {
            // Given
            val repository = providerLoginRepository()
            val sut = providerSut(repository)
            val email = "ibra@gmail.com"
            val password = "123456"
            val userData = UserEntity(password = password)
            val response = UserResponse(isSuccess = true, message = "success", data = userData)

            coEvery { repository.getUserData(email) } returns flowOf(response)

            // When
            val result = sut.invoke(email, password)

            // Then
            assertTrue(result is LoginStates.Success)
        }

    @Test
    fun `when call login and repository return response failed should return State Failed`() =
        runBlocking {
            // Given
            val repository = providerLoginRepository()
            val sut = providerSut(repository)
            val email = "ibra@gmail.com"
            val password = "123456"
            val response = UserResponse(isSuccess = false, message = "failed")

            coEvery { repository.getUserData(email) } returns flowOf(response)

            // When
            val result = sut.invoke(email, password)

            // Then
            assertTrue(result is LoginStates.Error && result.message == response.message)
        }

    @Test
    fun `when call login and repository return response but password is wrong should return Error`() = runBlocking {
        // Given
        val repository = providerLoginRepository()
        val sut = providerSut(repository)
        val email = "ibra@gmail.com"
        val password = "123456"
        val userData = UserEntity(password = "1234567")
        val response = UserResponse(isSuccess = true, message = "success", data = userData)

        coEvery { repository.getUserData(email) } returns flowOf(response)

        // When
        val result = sut.invoke(email, password)

        // Then
        assertTrue(result is LoginStates.Error && result.message == "Invalid password")
    }
}