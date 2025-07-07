package com.example.modsen_tasks_roman.domain.model.post

sealed class PostExceptionDomainModel(exception: Throwable): Throwable(exception) {
    
    override val cause: Throwable = exception
    
    class Other(exception: Throwable): PostExceptionDomainModel(exception)
    class NoInternet(exception: Throwable): PostExceptionDomainModel(exception)
}



