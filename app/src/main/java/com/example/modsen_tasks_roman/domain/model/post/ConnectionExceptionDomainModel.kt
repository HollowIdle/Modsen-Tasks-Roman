package com.example.modsen_tasks_roman.domain.model.post

sealed class ConnectionExceptionDomainModel(exception: Throwable): Throwable(exception) {
    
    override val cause: Throwable = exception
    
    class Other(exception: Throwable): ConnectionExceptionDomainModel(exception)
    class NoInternet(exception: Throwable): ConnectionExceptionDomainModel(exception)
}



