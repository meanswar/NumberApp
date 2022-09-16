package com.nikitosii.di.modules

import androidx.lifecycle.MutableLiveData
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.nikitosii.core.database.NumberDatabase
import com.nikitosii.core.database.dao.NumberFactDao
import com.nikitosii.core.database.entity.NumberFact
import dagger.Module
import dagger.Provides
import org.mockito.Mockito.mock
import javax.inject.Singleton

@Module
object TestDataBaseModule {

    @Provides
    @Singleton
    internal fun providesDatabase() = object : NumberDatabase() {

        override fun numberDao(): NumberFactDao = object: NumberFactDao {

            private val facts = mutableListOf<NumberFact>()

            override suspend fun getFacts(): List<NumberFact> = facts

            override fun insertFact(fact: NumberFact) {
                facts.add(fact)
            }

        }

        override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
            TODO("Not yet implemented")
        }

        override fun createInvalidationTracker(): InvalidationTracker {
            return mock(InvalidationTracker::class.java)
        }

        override fun clearAllTables() {
        }
    }

    @Provides
    @Singleton
    fun provideNumberDao(db: NumberDatabase) = db.numberDao()

}