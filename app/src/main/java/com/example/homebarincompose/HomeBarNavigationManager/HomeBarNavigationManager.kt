package com.example.homebarincompose.HomeBarNavigationManager

import android.app.Activity
import android.content.Intent
import com.example.homebarincompose.drinksDetails.ui.DrinksDetailsActivity
import com.example.homebarincompose.favourite.ui.FavouriteActivity
import com.example.homebarincompose.recipesearch.ui.SearchActivity
import com.example.homebarincompose.welcome.ui.WelcomeActivity

object HomeBarNavigationManager {

    fun Activity.navigateToSearchRecipe(){
        Intent(this, SearchActivity::class.java).apply {
            startActivity(this@apply)
        }
    }

    fun Activity.navigateToWelcomeActivity(){
        Intent(this, WelcomeActivity::class.java).apply {
            startActivity(this@apply)
        }
    }

    fun Activity.navigateToFavouriteActivity(){
        Intent(this, FavouriteActivity::class.java).apply {
            startActivity(this@apply)
        }
    }
    fun Activity.navigateToDetailsActivity(drinkId: Int){
        Intent(this, DrinksDetailsActivity::class.java).apply {
            this.putExtra("DRINK_ID", drinkId)
            startActivity(this@apply)
        }
    }


}