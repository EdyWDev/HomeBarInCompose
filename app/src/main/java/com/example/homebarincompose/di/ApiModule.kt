package com.example.homebarincompose.di

import android.app.Application
import android.content.Context
import com.example.homebarincompose.service.RecipeService
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

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create(gson))

    @Singleton
    @Provides
    fun provideRecipeService(retrofit: Retrofit.Builder): RecipeService =
        retrofit
            .build()
            .create(RecipeService::class.java)


    @Singleton
    @Provides
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }
}

