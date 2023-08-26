package com.example.ihsanstask.data.di.modules

import com.example.ihsanstask.data.di.impls.EventsRepositoryImpl
import com.example.ihsanstask.data.repositories.EventsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class EventsRepositoryModules {

    @Binds
    abstract fun providesModule(dataSource: EventsRepositoryImpl): EventsRepository
}
