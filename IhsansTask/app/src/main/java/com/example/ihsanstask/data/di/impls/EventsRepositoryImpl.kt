package com.example.ihsanstask.data.di.impls

import com.example.ihsanstask.data.db.DataBase
import com.example.ihsanstask.data.models.EventModel
import com.example.ihsanstask.data.repositories.EventsRepository
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    private val database: DataBase
) : EventsRepository {

    override suspend fun getEvents(): List<EventModel> = database.eventsDao().getEvents()

    override suspend fun removeEvent(id: Int) = database.eventsDao().removeItem(id)

}