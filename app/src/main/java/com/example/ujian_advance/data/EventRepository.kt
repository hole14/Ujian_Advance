package com.example.ujian_advance.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.ujian_advance.data.entity.EventEntity
import com.example.ujian_advance.data.retrofit.ApiService
import com.example.ujian_advance.data.room.EventDao
import com.example.ujian_advance.data.utils.AppExecutors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class EventRepository(private val eventDao: EventDao) {
    fun getAllFavoriteEvents(): Flow<List<EventEntity>> = eventDao.getAllFavoriteEvent()

    suspend fun insert(favoriteEvent: EventEntity) = eventDao.insert(favoriteEvent)

    suspend fun delete(favoriteEvent: EventEntity) = eventDao.delete(favoriteEvent)

    fun getFavoriteEventById(id: String): Flow<EventEntity?> =
        eventDao.getFavoriteEventById(id)
}