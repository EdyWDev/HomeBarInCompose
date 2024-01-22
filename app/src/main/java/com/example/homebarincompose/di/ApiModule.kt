package com.example.homebarincompose.di

import com.example.homebarincompose.service.HomeBarInComposeService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun providesGsonBuilder(): Gson =
        GsonBuilder()
            .create()
}

@Singleton
@Provides
fun provideRetrofit(gson: Gson): Retrofit.Builder =
    Retrofit.Builder()
        .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
        .addConverterFactory(GsonConverterFactory.create(gson))

@Singleton
@Provides
fun provideHomeBarInComposeService(retrofit: Retrofit.Builder): HomeBarInComposeService =
    retrofit
        .build()
        .create(HomeBarInComposeService::class.java)