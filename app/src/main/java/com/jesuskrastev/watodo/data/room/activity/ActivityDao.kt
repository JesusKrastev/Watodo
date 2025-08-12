package com.jesuskrastev.watodo.data.room.activity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ActivityDao {
    @Query("SELECT * FROM activities")
    suspend fun get(): List<ActivityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activity: ActivityEntity)

    @Query("DELETE FROM activities")
    suspend fun deleteAll()
}