package com.nikitosii.domain.usecase

import com.nikitosii.core.database.entity.NumberFact
import com.nikitosii.domain.repository.NumberFactRepository
import com.nikitosii.domain.usecase.base.UseCaseParams
import javax.inject.Inject

class InsertFactUseCase @Inject constructor(
    private val repo: NumberFactRepository
) : UseCaseParams<Unit, InsertFactUseCase.Params>() {
    class Params private constructor(val fact: NumberFact) {
        companion object {
            fun create(fact: NumberFact): Params = Params(fact)
        }
    }

    override suspend fun execute(params: Params) {
        repo.insertNumberFact(params.fact)
    }
}