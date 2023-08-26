package com.example.ihsanstask.data.dao

import androidx.room.*
import com.example.ihsanstask.data.models.EventModel
import kotlinx.coroutines.flow.Flow

@Dao
interface EventsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(contactModel: EventModel)

    @Query("SELECT * FROM eventModel")
    suspend fun getEvents(): List<EventModel>

    @Query("SELECT * FROM eventModel WHERE id= :id")
    suspend fun getEvent(id: Int): EventModel

    @Query("DELETE FROM eventModel")
    suspend fun clearMain()

    @Query("DELETE FROM eventModel WHERE id= :id")
    suspend fun removeItem(id: Int)

    @Update
    suspend fun updateEvent(model: EventModel)
}
