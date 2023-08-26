package com.example.ihsanstask.data.di.modules

import com.example.ihsanstask.data.di.impls.AddEventRepositoryImpl
import com.example.ihsanstask.data.repositories.AddEventRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class AddEventRepositoryModules {

    @Binds
    abstract fun providesModule(dataSource: AddEventRepositoryImpl): AddEventRepository
}
