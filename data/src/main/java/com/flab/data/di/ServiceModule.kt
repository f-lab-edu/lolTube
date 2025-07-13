package com.flab.data.di

import com.flab.data.repository.UserRepository
import com.flab.data.repository.UserRepositoryImpl
import com.flab.data.repository.LolTubeRepository
import com.flab.data.repository.LolTubeRepositoryImpl
import com.flab.data.service.RandomUserService
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
    fun provideRandomUserService(): RandomUserService {
        return RetrofitClient.createService()
    }

    @Provides
    fun provideUserRepository(randomUserService: RandomUserService): UserRepository {
        return UserRepositoryImpl(randomUserService)
    }

    @Provides
    fun provideYouTubeService(): LolTubeService {
        return RetrofitClient.createService()
    }

    @Provides
    fun provideYouTubeRepository(lolTubeService: LolTubeService): LolTubeRepository {
        return LolTubeRepositoryImpl(lolTubeService)
    }
}
