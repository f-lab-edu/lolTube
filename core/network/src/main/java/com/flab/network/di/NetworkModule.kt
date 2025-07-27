package com.flab.network.di

import com.flab.network.client.RetrofitClient
import com.flab.network.service.LolTubeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideLolTubeService(): LolTubeService {
        return RetrofitClient.createService()
    }
}
