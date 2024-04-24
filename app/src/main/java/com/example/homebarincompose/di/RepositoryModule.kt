package com.example.homebarincompose.di

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
        recipeService: RecipeService
    ): RecipeRepository = RecipeRepository(recipeService)
}