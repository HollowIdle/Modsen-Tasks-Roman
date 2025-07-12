package com.example.modsen_tasks_roman.ui.features.posts

import com.example.modsen_tasks_roman.R
import com.example.modsen_tasks_roman.domain.model.post.ConnectionExceptionDomainModel

fun ConnectionExceptionDomainModel.parseToString() = when (this) {
    is ConnectionExceptionDomainModel.Other -> R.string.posts_other_exception_text
    is ConnectionExceptionDomainModel.NoInternet -> R.string.posts_no_internet_exception_text
}