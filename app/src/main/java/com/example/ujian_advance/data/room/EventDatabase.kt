package com.example.ujian_advance.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ujian_advance.data.entity.EventEntity

@Database(entities = [EventEntity::class], version = 1)
abstract class EventDatabase: RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object {
        @Volatile
        private var INSTANCE: EventDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): EventDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventDatabase::class.java,
                    "favorite_event_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}