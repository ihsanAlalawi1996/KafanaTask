package com.example.ihsanstask.data.di.modules

import android.content.Context
import androidx.room.Room
import com.example.ihsanstask.data.db.DataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Volatile
    lateinit var databaseInstance: DataBase

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context): DataBase {
        databaseInstance = Room.databaseBuilder(app, DataBase::class.java, "Ihsans_task")
//            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

        return databaseInstance
    }
}
