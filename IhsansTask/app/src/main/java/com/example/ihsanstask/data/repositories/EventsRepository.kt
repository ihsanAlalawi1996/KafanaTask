package com.example.ihsanstask.data.repositories

import com.example.ihsanstask.data.models.EventModel

interface EventsRepository {

    suspend fun getEvents(): List<EventModel>
    suspend fun removeEvent(id: Int)

}
