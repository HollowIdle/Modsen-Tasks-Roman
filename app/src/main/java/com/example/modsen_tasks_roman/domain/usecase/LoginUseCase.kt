package com.example.modsen_tasks_roman.domain.usecase

import com.example.modsen_tasks_roman.domain.model.userCredentials.UserCredentialsDomainModel
import com.example.modsen_tasks_roman.domain.repository.IAuthRepository

class LoginUseCase (
    private val authRepository: IAuthRepository
)
{
    suspend operator fun invoke(credentials: UserCredentialsDomainModel): Result<Unit> =
        authRepository.validateCredentials(credentials)
}