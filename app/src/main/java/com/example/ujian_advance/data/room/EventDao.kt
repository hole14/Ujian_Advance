package com.example.ujian_advance.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ujian_advance.data.entity.EventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteEvent: EventEntity)

    @Delete
    suspend fun delete(favoriteEvent: EventEntity)

    @Query("SELECT * from events ORDER BY id ASC")
    fun getAllFavoriteEvent(): Flow<List<EventEntity>>

    @Query("SELECT * from events WHERE id = :id")
    fun getFavoriteEventById(id: String): Flow<EventEntity?>
}