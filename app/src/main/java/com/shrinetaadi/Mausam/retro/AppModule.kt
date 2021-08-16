package com.shrinetaadi.Mausam.retro

import com.shrinetaadi.Mausam.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun getRetroServiceInstance(retrofit: Retrofit): RetroServiceInstance {
        return retrofit.create(RetroServiceInstance::class.java)
    }


    @Singleton
    @Provides
    fun getRetroInstance(): Retrofit =
        Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}