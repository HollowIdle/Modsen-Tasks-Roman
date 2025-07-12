package com.example.modsen_tasks_roman.data.mapper

import android.util.Log
import com.example.modsen_tasks_roman.domain.model.post.ConnectionExceptionDomainModel
import java.net.ConnectException
import java.net.UnknownHostException

fun Throwable.toConnectionExceptionDomainModel(): ConnectionExceptionDomainModel {
    Log.e("!!", this.stackTraceToString())
    return when (this){
        is UnknownHostException, is ConnectException ->
            ConnectionExceptionDomainModel.NoInternet(this)
        is retrofit2.HttpException ->
            ConnectionExceptionDomainModel.Other(this)
        is ConnectionExceptionDomainModel -> this
        else -> ConnectionExceptionDomainModel.Other(this)
    }
}