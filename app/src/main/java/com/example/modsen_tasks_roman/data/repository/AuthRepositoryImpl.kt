package com.example.modsen_tasks_roman.data.repository

import com.example.modsen_tasks_roman.data.exceptions.InvalidCredentialsException
import com.example.modsen_tasks_roman.domain.model.UserCredentialsDomainModel
import com.example.modsen_tasks_roman.domain.repository.IAuthRepository

class AuthRepositoryImpl() : IAuthRepository {

    private val validUserCredentials = listOf(
        UserCredentialsDomainModel("user1", "password1"),
        UserCredentialsDomainModel("user2", "password2"),
        UserCredentialsDomainModel("admin", "admin123"),
    )

    override suspend fun validateCredentials(credentials: UserCredentialsDomainModel): Result<Unit> {
        kotlinx.coroutines.delay(1000)

        val isValid = validUserCredentials.any{
            it.login == credentials.login
                    && it.password == credentials.password
        }

        return if(isValid){
            Result.success(Unit)
        } else{
            Result.failure(InvalidCredentialsException())
        }

    }
}