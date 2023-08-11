package com.erkindilekci.valorantsphere.util

data class BaseResponse<T>(
    val status: Int,
    val data: T
)
