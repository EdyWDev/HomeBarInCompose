package com.example.homebarincompose.favourite.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
                        paddingValues = paddingValues,
                        viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun FavouriteDrinkScreen(
    favouriteDrinks: List<Drinks>,
    paddingValues: PaddingValues,
    viewModel: FavouriteViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        if(favouriteDrinks.isEmpty()){
            Text(
                text = "No favourite drinks yet!",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        } else{
            LazyColumn (
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                items(favouriteDrinks){drink ->
                    DrinkItem(drink = drink, onRemove = {viewModel.refreshFavouriteDrinks()})


                }
            }
        }

        favouriteDrinks.forEach { drink ->
            Text(drink.strDrink ?: "Unknown drink")
            Button(
                onClick = {
                    viewModel.removeFromFavourites(drink)
                }) {
                Text("Remove from favourites")
            }
        }
    }
}
@Composable
fun DrinkItem(drink: Drinks, onRemove: () -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ){
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = drink.strDrink ?: "Unknown drink", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(onClick = (onRemove)) {
                Text("Remove from favourites")
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