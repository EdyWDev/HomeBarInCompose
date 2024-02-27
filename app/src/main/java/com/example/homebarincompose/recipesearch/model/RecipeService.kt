package com.example.homebarincompose.recipesearch.model

import retrofit2.http.GET
import retrofit2.http.Url

interface RecipeService {

    @GET
    suspend fun getRecipe(@Url url: String) : DrinksDTO
    /* @GET("search.php?s=blue")
     suspend fun getRecipe() : RecipeDTO*/
    @GET
    suspend fun getRecipeById(@Url url: String) : DrinksDTO

}