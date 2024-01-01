package com.nikitosii.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikitosii.core.database.DBConfigs
import com.nikitosii.core.database.entity.NumberFact

@Dao
interface NumberFactDao {
    @Query("select * from ${DBConfigs.DB_FACTS_NAME}")
    suspend fun getFacts(): List<NumberFact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFact(fact: NumberFact)

}