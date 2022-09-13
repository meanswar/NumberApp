package com.nikitosii.util.ext

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.nikitosii.domain.base.WorkLiveData
import com.nikitosii.domain.base.WorkResult

fun <T> MutableLiveData<WorkResult<T>>.data() = this.value?.data

fun <T> MutableLiveData<WorkResult<List<T>>>.add(items: List<T>?) {
    val internalItems = items ?: emptyList()
    val data = this.data()
    this.value = WorkResult.success(
        if (data.isNullOrEmpty()) internalItems
        else data.plus(internalItems)
    )
}

fun <T> MutableLiveData<WorkResult<Pair<List<T>, Boolean>>>.add(pair: Pair<List<T>, Boolean>?) {
    val (items, hasMoreItems) = pair ?: emptyList<T>() to false
    val storedItems = this.data()?.first
    this.value = WorkResult.success(
        if (storedItems.isNullOrEmpty()) items to hasMoreItems
        else storedItems.plus(items) to hasMoreItems
    )
}

fun <T> MutableLiveData<T>.update(action: (T) -> T) {
    postValue(action(value ?: return))
}

fun <T, R> LiveData<WorkResult<T>>.map(action: (T?) -> WorkLiveData<R>): LiveData<WorkResult<R>> {
    return MediatorLiveData<WorkResult<R>>().apply {
        addSource(this@map) {
            when (it.status) {
                WorkResult.Status.SUCCESS -> WorkResult.success(action(it.data))
                WorkResult.Status.ERROR -> this.value = WorkResult.error(
                    message = it.message ?: it.exception?.message ?: "",
                    exception = it.exception
                )
                WorkResult.Status.LOADING -> WorkResult.loading(action(it.data))
            }
        }
    }
}