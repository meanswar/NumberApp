package com.nikitosii.domain.usecase

import com.nikitosii.TestConstants.ANY_DIGITS
import com.nikitosii.TestConstants.ANY_TEXT
import com.nikitosii.di.DaggerTestAppComponent
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
class GetRandomFactUseCaseTest {

    @Inject
    lateinit var getRandomFactUseCase: GetRandomFactUseCase

    @Before
    fun setUp() {
        DaggerTestAppComponent.builder()
            .build()
            .inject(this)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getRandomFact() = runBlockingTest {
        val res = getRandomFactUseCase.execute()
        assertTrue(res.id == ANY_DIGITS && res.text == ANY_TEXT && res.number == ANY_DIGITS)
    }
}