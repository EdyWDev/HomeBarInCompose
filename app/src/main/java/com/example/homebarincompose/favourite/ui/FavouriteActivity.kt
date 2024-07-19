package com.example.homebarincompose.favourite.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.example.homebarincompose.favourite.FavouriteViewModel
import com.example.homebarincompose.recipesearch.model.Drinks
import com.example.homebarincompose.ui.theme.HomeBarTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteActivity : ComponentActivity() {

    private val viewModel: FavouriteViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeBarTheme {
                val favouriteDrinks by viewModel.favouriteDrinks.collectAsState(initial = emptyList())
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Favourite Drinks") },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(Icons.Filled.ArrowBack, "back", tint = Color.Black)
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    FavouriteDrinkScreen(
                        favouriteDrinks = favouriteDrinks,
                        paddingValues = paddingValues
                    )
                }
            }
        }
    }
}

@Composable
fun FavouriteDrinkScreen(
    favouriteDrinks: List<Drinks>,
    paddingValues: PaddingValues
) {
}