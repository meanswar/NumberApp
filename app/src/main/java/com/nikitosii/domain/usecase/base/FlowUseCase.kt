package com.nikitosii.domain.usecase.base

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<T> {

    @WorkerThread
    abstract fun execute(): Flow<T>

}