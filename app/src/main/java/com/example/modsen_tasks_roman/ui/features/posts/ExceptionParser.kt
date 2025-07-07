package com.example.modsen_tasks_roman.ui.features.posts

import com.example.modsen_tasks_roman.R
import com.example.modsen_tasks_roman.domain.model.post.PostExceptionDomainModel

fun PostExceptionDomainModel.parseToString() = when (this) {
    is PostExceptionDomainModel.Other -> R.string.posts_other_exception_text
    is PostExceptionDomainModel.NoInternet -> R.string.posts_no_internet_exception_text
}