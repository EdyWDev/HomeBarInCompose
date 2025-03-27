package com.example.homebarincompose.service

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.homebarincompose.recipesearch.model.Drinks
import com.example.homebarincompose.recipesearch.model.Recipe
import com.example.homebarincompose.service.model.DrinksDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(
    private val homeBarInComposeService:
    RecipeService,
    context: Context
) {

    suspend fun getRecipeByCocktailName(cocktailName: String): Recipe {
        val url = "$COCKTAIL_BY_NAME$cocktailName"
        Log.d("API_REQUEST_NAME", "URL: $url")
        return homeBarInComposeService.getRecipe(url).toDomainRecipeModel()
        Log.d("API_RESPONSE_NAME", "Response")
    }

    suspend fun getRecipeByIngredients(ingredients: String): Recipe{
        val url = "$COCKTAIL_BY_INGREDIENT$ingredients"
        Log.d("API_REQUEST_INGREDIENTS", "URL: $url")
        return homeBarInComposeService.getRecipe(url).toDomainRecipeModel()
    }

    suspend fun getRandomDrink(): Drinks? {
        val url = RANDOM_COCKTAIL
        return homeBarInComposeService.getRandomDrink(url).toDomainRecipeModel().drinks[0]
    }

    suspend fun getDrinkByID(id: String): Drinks?{
        val url = "$COCKTAIL_BY_ID$id"
        return homeBarInComposeService.getRecipeById(url).toDomainRecipeModel().drinks[0]
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("favorites", Context.MODE_PRIVATE)


    private val favouriteDrinks = mutableSetOf<String>()

    suspend fun isFavourite(drinkId: String): Boolean{
        return favouriteDrinks.contains(drinkId)
    }

    fun addFavourite(drink: Drinks){
        favouriteDrinks.add((drink.idDrink.toString()))
        saveFavouritesDrinks()
    }

    fun removeFavourite(drinkId: String){
        favouriteDrinks.remove(drinkId)
        saveFavouritesDrinks()
    }

    suspend fun getFavouriteDrinks(): List<Drinks>{
        return favouriteDrinks.mapNotNull { id -> getDrinkByID(id) }
    }
    private fun saveFavouritesDrinks(){
        val editor = sharedPreferences.edit()
        editor.putString("favouriteDrinks", favouriteDrinks.toString())
        editor.apply()
    }

    private fun loadFavouriteDrinks(){
        val savedFavouriteDrinks = sharedPreferences.getStringSet("favouriteDrinks", mutableSetOf()) ?: mutableSetOf()
        favouriteDrinks.clear()
        favouriteDrinks.addAll(savedFavouriteDrinks)
    }
    init {
        loadFavouriteDrinks()
    }
}


fun DrinksDTO?.toDomainRecipeModel(): Recipe{
    return Recipe(drinks=this?.drinks?.map {
        Drinks(
            idDrink = it.idDrink,
            strDrink = it.strDrink,
            strDrinkAlternate = it.strDrinkAlternate,
            strTags = it.strTags,
            strVideo = it.strVideo,
            strCategory = it.strCategory,
            strIBA = it.strIBA,
            strAlcoholic = it.strAlcoholic,
            strGlass = it.strGlass,
            strInstructions = it.strInstructions,
            strInstructionsES = it.strInstructionsES,
            strInstructionsDE = it.strInstructionsDE,
            strInstructionsFR = it.strInstructionsFR,
            strInstructionsIT = it.strInstructionsIT,
            strDrinkThumb = it.strDrinkThumb,
            strIngredient1 = it.strIngredient1,
            strIngredient2 = it.strIngredient2,
            strIngredient3 = it.strIngredient3,
            strIngredient4 = it.strIngredient4,
            strIngredient5 = it.strIngredient5,
            strIngredient6 = it.strIngredient6,
            strIngredient7 = it.strIngredient7,
            strIngredient8 = it.strIngredient8,
            strIngredient9 = it.strIngredient9,
            strIngredient10 = it.strIngredient10,
            strIngredient11 = it.strIngredient11,
            strIngredient12 = it.strIngredient12,
            strIngredient13 = it.strIngredient13,
            strIngredient14 = it.strIngredient14,
            strIngredient15 = it.strIngredient15,
            strMeasure1 = it.strMeasure1,
            strMeasure2 = it.strMeasure2,
            strMeasure3 = it.strMeasure3,
            strMeasure4 = it.strMeasure4,
            strMeasure5 = it.strMeasure5,
            strMeasure6 = it.strMeasure6,
            strMeasure7 = it.strMeasure7,
            strMeasure8 = it.strMeasure8,
            strMeasure9 = it.strMeasure9,
            strMeasure10 = it.strMeasure10,
            strMeasure11 = it.strMeasure11,
            strMeasure12 = it.strMeasure12,
            strMeasure13 = it.strMeasure13,
            strMeasure14 = it.strMeasure14,
            strMeasure15 = it.strMeasure15,
            strImageSource = it.strImageSource,
            strImageAttribution = it.strImageAttribution,
            strCreativeCommonsConfirmed = it.strCreativeCommonsConfirmed,
            dateModified = it.dateModified
        )
    } ?: emptyList())
}

const val COCKTAIL_BY_NAME = "search.php?s="
const val COCKTAIL_BY_INGREDIENT = "filter.php?i="
const val COCKTAIL_BY_ALCOHOL = "filter.php?a="
const val COCKTAIL_BY_ID = "lookup.php?i="
const val RANDOM_COCKTAIL = "random.php"