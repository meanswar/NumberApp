package com.nikitosii.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikitosii.core.database.DBConfigs.DB_FACTS_NAME

@Entity(tableName = DB_FACTS_NAME)
data class NumberFact(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val number: Int,
    val text: String
)