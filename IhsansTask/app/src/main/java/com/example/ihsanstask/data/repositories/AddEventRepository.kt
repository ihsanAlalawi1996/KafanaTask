package com.example.ihsanstask.data.repositories

import com.example.ihsanstask.data.models.EventModel

interface AddEventRepository {

    suspend fun addEvent(model: EventModel)
    suspend fun updateEvent(model: EventModel)
    suspend fun getEvent(id: Int): EventModel

}
