package com.nikitosii.di.modules

import com.nikitosii.TestConstants.ANY_DIGITS
import com.nikitosii.TestConstants.ANY_TEXT
import com.nikitosii.api.NumberApi
import com.nikitosii.core.database.entity.NumberFact
import com.nikitosii.di.module.NetErrorModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [NetErrorModule::class]
)
class TestNetworkModule {

    @Provides
    @Singleton
    internal fun provideNumberApi() = object : NumberApi {
        override suspend fun getFact(id: Int): NumberFact {
            return NumberFact(ANY_DIGITS, id, ANY_TEXT)
        }

        override suspend fun getRandomNumber(): NumberFact {
            return NumberFact(ANY_DIGITS, ANY_DIGITS, ANY_TEXT)
        }
    }
}