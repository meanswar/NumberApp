package com.nikitosii.flow.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitosii.core.database.entity.NumberFact
import com.nikitosii.domain.base.WorkLiveData
import com.nikitosii.domain.base.WorkResult
import com.nikitosii.domain.usecase.GetFactUseCase
import com.nikitosii.domain.usecase.GetLocalFactsUseCase
import com.nikitosii.domain.usecase.GetRandomFactUseCase
import com.nikitosii.domain.usecase.InsertFactUseCase
import com.nikitosii.flow.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getFactsUseCase: GetLocalFactsUseCase,
    private val getFactUseCase: GetFactUseCase,
    private val getRandomFactUseCase: GetRandomFactUseCase,
    private val insertFactUseCase: InsertFactUseCase
) : BaseViewModel() {
    val _facts = WorkLiveData<List<NumberFact>>()
    val facts: LiveData<WorkResult<List<NumberFact>>>
        get() = _facts
    private val _fact = WorkLiveData<NumberFact>()
    val fact: LiveData<WorkResult<NumberFact>>
        get() = _fact
    val number by lazy { MutableLiveData<String>() }

    fun getNewFact() {
        number.value?.let {
            val params = GetFactUseCase.Params.create(it.toInt())
            ioToUiWorkData(
                io = { getFactUseCase.execute(params) },
                ui = { _fact.postValue(it) }
            )
        }
    }

    fun getFacts() = ioToUiWorkData(
        io = { getFactsUseCase.execute() },
        ui = { _facts.postValue(it) }
    )

    fun insertFact() = ioToUnit {
        _fact.value?.data?.let {
            val params = InsertFactUseCase.Params.create(it)
            insertFactUseCase.execute(params)
        }
    }

    fun getRandomFact() = ioToUiWorkData(
        io = { getRandomFactUseCase.execute() },
        ui = { _fact.postValue(it) }
    )
}