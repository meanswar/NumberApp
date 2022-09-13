package com.nikitosii.domain.repository

import com.nikitosii.core.base.channel.Status
import com.nikitosii.core.database.entity.NumberFact
import kotlinx.coroutines.flow.Flow

interface NumberFactRepository {

    suspend fun getNumberFact(id: Int): NumberFact

    suspend fun getRandomFact(): NumberFact

    suspend fun getLocalFacts(): List<NumberFact>

    fun getNumberFacts(): Flow<Status<List<NumberFact>>>

    suspend fun insertNumberFact(fact: NumberFact)
}