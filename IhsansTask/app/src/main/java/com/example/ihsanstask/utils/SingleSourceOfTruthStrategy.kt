package com.example.ihsanstask.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

suspend fun <A> resultFlowData(networkCall: suspend () -> Results<A>): Flow<Results<A?>> = flow {
    emit(Results.loading())
    val responseStatus = networkCall.invoke()

    if (responseStatus.status == Results.Status.SUCCESS) {
        emit(Results.success(data = responseStatus.data))
    } else if (responseStatus.status == Results.Status.ERROR) {
        emit(
            Results.error(
                data = responseStatus.data,
                code = responseStatus.code,
                message = responseStatus.message
            )
        )
    }

}.flowOn(Dispatchers.IO)

suspend fun <A> resultFlow(call: A): Flow<A> = flow {
    emit(call)
}.flowOn(Dispatchers.IO)
