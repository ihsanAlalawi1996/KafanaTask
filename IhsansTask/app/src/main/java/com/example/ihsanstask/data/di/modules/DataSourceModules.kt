package com.example.ihsanstask.data.di.modules

import com.example.ihsanstask.data.di.impls.RemoteDataSourceImpl
import com.example.ihsanstask.utils.DataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModules {

    @Binds
    abstract fun providesModule(dataSource: RemoteDataSourceImpl): DataSource
}
