package com.nikitosii.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nikitosii.core.database.DBConfigs.DB_VERSION
import com.nikitosii.core.database.dao.NumberFactDao
import com.nikitosii.core.database.entity.NumberFact

@Database(entities = [NumberFact::class], version = DB_VERSION)
abstract class NumberDatabase: RoomDatabase() {
    abstract fun numberDao(): NumberFactDao
}