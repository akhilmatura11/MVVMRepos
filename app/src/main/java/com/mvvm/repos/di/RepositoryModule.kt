package com.mvvm.repos.di

import android.content.Context
import com.mvvm.repos.data.DataRepository
import com.mvvm.repos.data.local.LocalDataSource
import com.mvvm.repos.data.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        @ApplicationContext context: Context,
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): DataRepository {
        return DataRepository(context, localDataSource, remoteDataSource)
    }
}
