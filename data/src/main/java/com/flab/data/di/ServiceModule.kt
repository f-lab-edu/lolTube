package com.flab.data.di

import com.flab.data.repository.LolTubeRepository
import com.flab.data.repository.LolTubeRepositoryImpl
import com.flab.data.service.RetrofitClient
import com.flab.data.service.LolTubeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun provideYouTubeService(): LolTubeService {
        return RetrofitClient.createService()
    }

    @Provides
    fun provideYouTubeRepository(lolTubeService: LolTubeService): LolTubeRepository {
        return LolTubeRepositoryImpl(lolTubeService)
    }
}
