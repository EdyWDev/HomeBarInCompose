package com.example.homebarincompose.HomeBarNavigationManager

import android.app.Activity
import android.content.Intent
import com.example.homebarincompose.recipesearch.ui.SearchActivity

object HomeBarNavigationManager {

    fun Activity.navigateToSearchRecipe(){
        Intent(this, SearchActivity::class.java).apply {
            startActivity(this@apply)
        }
    }
}