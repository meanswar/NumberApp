package com.nikitosii.domain.usecase.base

import androidx.annotation.WorkerThread

abstract class UseCaseParams<T, Params> {

    @WorkerThread
    abstract suspend fun execute(params: Params): T
}