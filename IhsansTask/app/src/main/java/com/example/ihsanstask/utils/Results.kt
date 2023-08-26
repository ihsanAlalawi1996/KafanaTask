package com.example.ihsanstask.utils

data class Results<out T>(val status: Status, val data: T?, val code: Int?, val message: String?) {

    enum class Status { START, SUCCESS, ERROR, LOADING }

    companion object {
        fun <T> start(): Results<T> =
            Results(Status.START, null, null, null)

        fun <T> success(data: T, code: Int? = null): Results<T> =
            Results(Status.SUCCESS, data, code, null)

        fun <T> error(data: T? = null, code: Int? = null, message: String? = null): Results<T> =
            Results(Status.ERROR, data, code, message)

        fun <T> loading(data: T? = null): Results<T> =
            Results(Status.LOADING, data, null, null)

    }
}
