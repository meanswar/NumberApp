package com.nikitosii.di.module

import android.content.Context
import androidx.room.Room
import com.nikitosii.core.database.DBConfigs
import com.nikitosii.core.database.NumberDatabase
import com.nikitosii.core.database.dao.NumberFactDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {
    @Provides
    @Singleton
    internal fun provideDatabase(context: Context) = Room.databaseBuilder(
        context,
        NumberDatabase::class.java,
        DBConfigs.DB_NAME
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    internal fun provideNumberFactDao(db: NumberDatabase): NumberFactDao = db.numberDao()
}