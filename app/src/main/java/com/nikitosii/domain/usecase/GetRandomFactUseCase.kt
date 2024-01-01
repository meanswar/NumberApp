package com.nikitosii.domain.usecase

import com.nikitosii.core.database.entity.NumberFact
import com.nikitosii.domain.repository.NumberFactRepository
import com.nikitosii.domain.usecase.base.BaseUseCase
import com.nikitosii.domain.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRandomFactUseCase @Inject constructor(
    private val repo: NumberFactRepository
): BaseUseCase<NumberFact>() {

    override suspend fun execute(): NumberFact = repo.getRandomFact()
}