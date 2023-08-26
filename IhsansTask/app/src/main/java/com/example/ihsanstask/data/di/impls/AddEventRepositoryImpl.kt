package com.example.ihsanstask.data.di.impls

import com.example.ihsanstask.data.db.DataBase
import com.example.ihsanstask.data.endpoints.Endpoints
import com.example.ihsanstask.data.models.EventModel
import com.example.ihsanstask.data.repositories.AddEventRepository
import com.example.ihsanstask.utils.DataSource
import javax.inject.Inject

class AddEventRepositoryImpl @Inject constructor(
    private val database: DataBase
) : AddEventRepository {

    override suspend fun addEvent(model: EventModel) {
        database.eventsDao().insertItem(model)
    }

    override suspend fun updateEvent(model: EventModel) {
        database.eventsDao().updateEvent(model)
    }

    override suspend fun getEvent(id: Int): EventModel = database.eventsDao().getEvent(id)
}