package com.nikitosii.domain.base

data class WorkResult<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val exception: Throwable? = null
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): WorkResult<T> {
            return WorkResult(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(
            message: String,
            data: T? = null,
            exception: Throwable? = null
        ): WorkResult<T> {
            return WorkResult(
                Status.ERROR,
                data,
                message,
                exception
            )
        }

        fun <T> loading(data: T? = null): WorkResult<T> {
            return WorkResult(
                Status.LOADING,
                data,
                null
            )
        }

    }
}