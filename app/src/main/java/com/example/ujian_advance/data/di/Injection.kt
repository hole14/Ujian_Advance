package com.example.ujian_advance.data.di

import android.content.Context
import com.example.ujian_advance.data.EventRepository
import com.example.ujian_advance.data.room.EventDatabase
import com.example.ujian_advance.ui.setting.switchmode.SettingPreferences
import com.example.ujian_advance.ui.setting.switchmode.dataStore

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val database by lazy { EventDatabase.getDatabase(context) }
        return EventRepository(database.eventDao())
    }

    fun provideSettingPreferences(context: Context): SettingPreferences {
        return SettingPreferences.getInstance(context.dataStore)
    }
}