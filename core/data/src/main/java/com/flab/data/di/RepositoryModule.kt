package com.flab.data.di

import com.flab.data.repository.LolTubeRepository
import com.flab.data.repository.LolTubeRepositoryImpl
import com.flab.network.service.LolTubeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideLolTubeRepository(lolTubeService: LolTubeService): LolTubeRepository {
        return LolTubeRepositoryImpl(lolTubeService)
    }
}
