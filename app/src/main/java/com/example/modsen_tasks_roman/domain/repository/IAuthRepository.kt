package com.example.modsen_tasks_roman.domain.repository

import com.example.modsen_tasks_roman.domain.model.userCredentials.UserCredentialsDomainModel

interface IAuthRepository{
    suspend fun validateCredentials(credentials: UserCredentialsDomainModel): Result<Unit>
}