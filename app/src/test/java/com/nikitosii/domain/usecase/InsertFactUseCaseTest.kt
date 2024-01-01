package com.nikitosii.domain.usecase

import com.nikitosii.TestConstants.ANY_DIGITS
import com.nikitosii.TestConstants.ANY_TEXT
import com.nikitosii.core.database.dao.NumberFactDao
import com.nikitosii.core.database.entity.NumberFact
import com.nikitosii.di.DaggerTestAppComponent
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class InsertFactUseCaseTest {
    @Inject
    lateinit var insertFactUseCase: InsertFactUseCase

    @Inject
    lateinit var dao: NumberFactDao

    @Before
    fun setUp() {
        DaggerTestAppComponent.builder()
            .build()
            .inject(this)
    }

    @Test
    fun insertFact() = runBlockingTest {
        val params = InsertFactUseCase.Params.create(NumberFact(ANY_DIGITS, ANY_DIGITS, ANY_TEXT))
        insertFactUseCase.execute(params)
        assertTrue(dao.getFacts().size == 1)
    }
}