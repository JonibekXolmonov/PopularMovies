package com.example.android_imperative.di

import com.example.android_imperative.networking.Server.IS_TESTER
import com.example.android_imperative.networking.Server.SERVER_DEVELOPMENT
import com.example.android_imperative.networking.Server.SERVER_PRODUCTION
import com.example.android_imperative.networking.TVShowService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModel {

    /*
    Retrofit Related
     */
    @Provides
    fun server(): String = if (IS_TESTER) SERVER_PRODUCTION else SERVER_DEVELOPMENT

    @Provides
    @Singleton
    fun retrofitClient(): Retrofit =
        Retrofit.Builder()
            .baseUrl(server())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun tvShowService(): TVShowService = retrofitClient().create(TVShowService::class.java)

    /*
    Room Related
     */

}