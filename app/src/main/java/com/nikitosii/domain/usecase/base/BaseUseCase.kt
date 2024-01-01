package com.nikitosii.domain.usecase.base

import androidx.annotation.WorkerThread

abstract class BaseUseCase<T> {
    @WorkerThread
    abstract suspend fun execute(): T
}