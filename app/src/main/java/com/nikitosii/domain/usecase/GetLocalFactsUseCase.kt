package com.nikitosii.domain.usecase

import com.nikitosii.core.database.entity.NumberFact
import com.nikitosii.domain.repository.NumberFactRepository
import com.nikitosii.domain.usecase.base.BaseUseCase
import javax.inject.Inject

class GetLocalFactsUseCase @Inject constructor(private val repo: NumberFactRepository) :
    BaseUseCase<List<NumberFact>>() {
    override suspend fun execute(): List<NumberFact> = repo.getLocalFacts()
}