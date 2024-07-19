package com.example.homebarincompose.di

import android.content.Context
import com.example.homebarincompose.service.RecipeRepository
import com.example.homebarincompose.service.RecipeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideHomeBarInComposeRepository(
        context: Context,
        recipeService: RecipeService
    ): RecipeRepository = RecipeRepository(recipeService, context)
}