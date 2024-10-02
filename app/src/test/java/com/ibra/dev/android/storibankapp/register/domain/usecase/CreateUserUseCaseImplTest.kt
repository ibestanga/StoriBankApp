package com.ibra.dev.android.storibankapp.register.domain.usecase

import com.ibra.dev.android.storibankapp.core.data.entities.UserResponse
import com.ibra.dev.android.storibankapp.login.domain.models.UserSingUpDto
import com.ibra.dev.android.storibankapp.register.data.contracts.RegisterRepository
import com.ibra.dev.android.storibankapp.register.presentations.states.RegisterScreenStates
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class CreateUserUseCaseImplTest {

    private fun providerRepository(): RegisterRepository = mockk(relaxed = true)

    private fun providerSut(repository: RegisterRepository): CreateUserUseCaseImpl {
        return CreateUserUseCaseImpl(repository)
    }

    @Test
    fun `when call invoke and repository return flow with success response then should return success`() =
        runTest {
            // Given
            val repository = providerRepository()
            val sut = providerSut(repository)
            val user = UserSingUpDto("email", "password")
            val userResponse = UserResponse(true, "success")

            coEvery { repository.registerUser(any()) } returns flowOf(userResponse)

            // When
            val result = sut.invoke(user)

            // Then
            assertTrue(result is RegisterScreenStates.Success)
        }

    @Test
    fun `when call invoke and repository return flow with error response then should return error`() =
        runTest {
            // Given
            val repository = providerRepository()
            val sut = providerSut(repository)
            val user = UserSingUpDto("email", "password")
            val userResponse = UserResponse(false, "error")

            coEvery { repository.registerUser(any()) } returns flowOf(userResponse)

            // When
            val result = sut.invoke(user)

            // Then
            assertTrue(result is RegisterScreenStates.Error)
        }
}