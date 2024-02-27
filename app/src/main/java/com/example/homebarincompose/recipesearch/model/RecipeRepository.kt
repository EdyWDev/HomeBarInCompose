
package com.example.homebarincompose.recipesearch.model


class RecipeRepository (
    private val recipeService: RecipeService
        ){
    suspend fun getRecipeByCocktailName(cocktailName: String): Recipe{
        val url = "$COCKTAIL_BY_NAME$cocktailName"
        return recipeService.getRecipe(url).toDomainRecipeModel()
    }

    suspend fun getRecipeByIngredients(ingredients: String): Recipe{
        val url = "$COCKTAIL_BY_INGREDIENT$ingredients"
        return recipeService.getRecipe(url).toDomainRecipeModel()
    }
}

fun DrinksDTO?.toDomainRecipeModel(): Recipe{
    return Recipe(drinks = this?.drinks?.map {
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
    })
}

const val COCKTAIL_BY_NAME = "search.php?s="
const val COCKTAIL_BY_INGREDIENT = "filter.php?i="

/*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepository (
    private val homeBarService: RecipeService
        ){
    suspend fun getRecipeByCocktailName(cocktailName: String): Recipe {
        val url = "$COCKTAIL_BY_NAME$cocktailName"
        return homeBarService.getRecipe(url).toDomainRecipeModel()
    }

    suspend fun getRecipeByIngredients(ingredients: String): Recipe {
        val url = "$COCKTAIL_BY_INGREDIENT$ingredients"
        return homeBarService.getRecipe(url).toDomainRecipeModel()
    }

    suspend fun getRecipeByGlass(glass: String): Recipe {
        val url = "$COCKTAIL_BY_GLASS$glass"
        return homeBarService.getRecipe(url).toDomainRecipeModel()
    }

    suspend fun getRecipeByAlcohol(alcohol: String): Recipe {
        val url = "$COCKTAIL_BY_ALCOHOL$alcohol"
        return homeBarService.getRecipe(url).toDomainRecipeModel()
    }


    suspend fun getRandomCocktail(): Drinks? =
        withContext(Dispatchers.IO) {
            val url = RANDOM_COCKTAIL
            homeBarService.getRecipe(url).toDomainRecipeModel().drinks?.get(0)
        }

    suspend fun getRecipeByID(id: String): Drinks? {
        val url = "$COCKTAIL_BY_ID$id"
        return homeBarService.getRecipeById(url).toDomainRecipeModel().drinks?.get(0)
    }
}
fun DrinksDTO?.toDomainRecipeModel(): Recipe {
    return Recipe(drinks = this?.drinks?.map {
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
    })
}

fun DrinksModel.toDomainDrinksModel(): Drinks {
    return Drinks(
        idDrink = this.idDrink,
        strDrink = this.strDrink,
        strDrinkAlternate = this.strDrinkAlternate,
        strTags = this.strTags,
        strVideo = this.strVideo,
        strCategory = this.strCategory,
        strIBA = this.strIBA,
        strAlcoholic = this.strAlcoholic,
        strGlass = this.strGlass,
        strInstructions = this.strInstructions,
        strInstructionsES = this.strInstructionsES,
        strInstructionsFR = this.strInstructionsFR,
        strInstructionsIT = this.strInstructionsIT,
        strDrinkThumb = this.strDrinkThumb,
        strIngredient1 = this.strIngredient1,
        strIngredient2 = this.strIngredient2,
        strIngredient3 = this.strIngredient3,
        strIngredient4 = this.strIngredient4,
        strIngredient5 = this.strIngredient5,
        strIngredient6 = this.strIngredient6,
        strIngredient7 = this.strIngredient7,
        strIngredient8 = this.strIngredient8,
        strIngredient9 = this.strIngredient9,
        strIngredient10 = this.strIngredient10,
        strIngredient11 = this.strIngredient11,
        strIngredient12 = this.strIngredient12,
        strIngredient13 = this.strIngredient13,
        strIngredient14 = this.strIngredient14,
        strIngredient15 = this.strIngredient15,
        strMeasure1 = this.strMeasure1,
        strMeasure2 = this.strMeasure2,
        strMeasure3 = this.strMeasure3,
        strMeasure4 = this.strMeasure4,
        strMeasure5 = this.strMeasure5,
        strMeasure6 = this.strMeasure6,
        strMeasure7 = this.strMeasure7,
        strMeasure8 = this.strMeasure8,
        strMeasure9 = this.strMeasure9,
        strMeasure10 = this.strMeasure10,
        strMeasure11 = this.strMeasure11,
        strMeasure12 = this.strMeasure12,
        strMeasure13 = this.strMeasure13,
        strMeasure14 = this.strMeasure14,
        strMeasure15 = this.strMeasure15,
        strImageSource = this.strImageSource,
        strImageAttribution = this.strImageAttribution,
        strCreativeCommonsConfirmed = this.strCreativeCommonsConfirmed,
        dateModified = this.dateModified,
        strInstructionsDE = null
    )
}


const val COCKTAIL_BY_NAME = "search.php?s="
const val COCKTAIL_BY_INGREDIENT = "filter.php?i="
const val COCKTAIL_BY_GLASS = "filter.php?g="
const val COCKTAIL_BY_ALCOHOL = "filter.php?a="
const val COCKTAIL_BY_ID = "lookup.php?i="
const val RANDOM_COCKTAIL = "random.php"
*/
