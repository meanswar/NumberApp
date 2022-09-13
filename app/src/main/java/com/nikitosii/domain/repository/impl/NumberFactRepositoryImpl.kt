package com.nikitosii.domain.repository.impl

import com.nikitosii.api.NumberApi
import com.nikitosii.core.base.channel.ErrorHandler
import com.nikitosii.core.base.channel.Status
import com.nikitosii.core.base.connectivity.ConnectivityProvider
import com.nikitosii.core.database.dao.NumberFactDao
import com.nikitosii.core.database.entity.NumberFact
import com.nikitosii.domain.repository.NumberFactRepository
import com.nikitosii.domain.repository.base.BaseRepo
import com.nikitosii.domain.repository.base.ChannelRecreateObserver
import com.nikitosii.domain.repository.base.repoChannel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class NumberFactRepositoryImpl @Inject constructor(
    private val api: NumberApi,
    errorHandler: ErrorHandler,
    private val dao: NumberFactDao,
    connectivityProvider: ConnectivityProvider,
    io: CoroutineDispatcher,
    channelRecreateObserver: ChannelRecreateObserver
) : BaseRepo(errorHandler), NumberFactRepository {

    private val numberFactsChannel by repoChannel<List<NumberFact>>(
        io,
        connectivityProvider,
        channelRecreateObserver
    ) {
        storageConfig {
            get = { dao.getFacts() }
        }
    }

    override suspend fun getNumberFact(id: Int): NumberFact = runWithErrorHandler { api.getFact(id) }

    override suspend fun getRandomFact(): NumberFact = runWithErrorHandler { api.getRandomNumber() }

    override suspend fun getLocalFacts(): List<NumberFact> = runWithErrorHandler { dao.getFacts() }

    override fun getNumberFacts(): Flow<Status<List<NumberFact>>> = numberFactsChannel.flow

    override suspend fun insertNumberFact(fact: NumberFact) = runWithErrorHandler {
        dao.insertFact(fact)
    }
}