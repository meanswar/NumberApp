package com.nikitosii.domain.usecase

import com.nikitosii.TestConstants
import com.nikitosii.TestConstants.ANY_DIGITS
import com.nikitosii.TestConstants.ANY_TEXT
import com.nikitosii.di.DaggerTestAppComponent
import junit.framework.Assert
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetFactUseCaseTest {
    @Inject
    lateinit var getFactUseCase: GetFactUseCase

    @Before
    fun setUp() {
        DaggerTestAppComponent.builder()
            .build()
            .inject(this)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getRandomFact() = runBlockingTest {
        val params = GetFactUseCase.Params.create(ANY_DIGITS)
        val res = getFactUseCase.execute(params)
        assertTrue(res.id == ANY_DIGITS && res.text == ANY_TEXT && res.number == ANY_DIGITS)
    }
}