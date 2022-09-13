package com.nikitosii.domain.usecase

import com.nikitosii.core.database.entity.NumberFact
import com.nikitosii.domain.repository.NumberFactRepository
import com.nikitosii.domain.usecase.base.UseCaseParams
import javax.inject.Inject

class GetFactUseCase @Inject constructor(
    private val repo: NumberFactRepository
) : UseCaseParams<NumberFact, GetFactUseCase.Params>() {
    class Params private constructor(val id: Int) {
        companion object {
            fun create(id: Int): Params = Params(id)
        }
    }

    override suspend fun execute(params: Params): NumberFact =
        repo.getNumberFact(params.id)
}