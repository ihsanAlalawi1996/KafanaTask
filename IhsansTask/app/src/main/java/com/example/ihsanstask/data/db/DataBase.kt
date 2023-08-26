package com.example.ihsanstask.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ihsanstask.data.dao.EventsDao
import com.example.ihsanstask.data.models.EventModel

@Database(
    entities = [EventModel::class],
    version = 1,
    exportSchema = false
)
abstract class DataBase : RoomDatabase() {

    abstract fun eventsDao(): EventsDao
}
