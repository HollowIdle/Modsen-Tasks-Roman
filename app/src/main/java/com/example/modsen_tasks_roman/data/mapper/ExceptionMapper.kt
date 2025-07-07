package com.example.modsen_tasks_roman.data.mapper

import android.util.Log
import com.example.modsen_tasks_roman.domain.model.post.PostExceptionDomainModel
import java.net.ConnectException
import java.net.UnknownHostException

fun Throwable.toPostExceptionDomainModel(): PostExceptionDomainModel {
    Log.e("!!", this.stackTraceToString())
    return when (this){
        is UnknownHostException, is ConnectException ->
            PostExceptionDomainModel.NoInternet(this)
        is retrofit2.HttpException ->
            PostExceptionDomainModel.Other(this)
        is PostExceptionDomainModel -> this
        else -> PostExceptionDomainModel.Other(this)
    }
}