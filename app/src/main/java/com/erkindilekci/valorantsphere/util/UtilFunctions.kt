package com.erkindilekci.valorantsphere.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <T> wrapWithFlow(function: suspend () -> Response<T>): Flow<Resource<T>> {
    return flow {
        try {
            val result = function()
            emit(Resource.Loading())
            if (result.isSuccessful) {
                result.body()?.let {
                    emit(Resource.Success(it))
                }
            } else {
                emit(Resource.Error(result.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}