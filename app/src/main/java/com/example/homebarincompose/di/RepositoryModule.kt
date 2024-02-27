package com.example.homebarincompose.di

import com.example.homebarincompose.recipesearch.model.RecipeRepository
import com.example.homebarincompose.recipesearch.model.RecipeService
import com.example.homebarincompose.service.HomeBarInComposeRepository
import com.example.homebarincompose.service.HomeBarInComposeService
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