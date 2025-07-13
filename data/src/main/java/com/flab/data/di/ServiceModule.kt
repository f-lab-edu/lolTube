package com.flab.data.di

import com.flab.data.repository.UserRepository
import com.flab.data.repository.UserRepositoryImpl
import com.flab.data.service.RandomUserService
import com.flab.data.service.RetrofitClient
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
}
