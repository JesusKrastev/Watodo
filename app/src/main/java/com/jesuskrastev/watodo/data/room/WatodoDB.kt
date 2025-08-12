package com.jesuskrastev.watodo.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jesuskrastev.watodo.data.room.activity.ActivityDao
import com.jesuskrastev.watodo.data.room.activity.ActivityEntity

@Database(
    entities = [ActivityEntity::class],
    version = 1,
)
abstract class WatodoDB: RoomDatabase() {
    abstract fun activityDao(): ActivityDao

    companion object {
        fun getDatabase(
            context: Context
        ) = Room.databaseBuilder(
            context = context,
            WatodoDB::class.java,
            "watodo"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}