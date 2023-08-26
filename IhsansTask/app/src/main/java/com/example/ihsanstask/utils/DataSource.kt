package com.example.ihsanstask.utils

import retrofit2.Response

interface DataSource {

    suspend fun <T> getResult(call: suspend () -> Response<T>): Results<T>

}

