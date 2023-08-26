package com.example.ihsanstask.data.di.modules

import com.example.ihsanstask.data.di.impls.DateRepositoryImpl
import com.example.ihsanstask.data.di.impls.RemoteDataSourceImpl
import com.example.ihsanstask.data.repositories.DateRepository
import com.example.ihsanstask.utils.DataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DateRepositoryModules {

    @Binds
    abstract fun providesModule(dataSource: DateRepositoryImpl): DateRepository
}
