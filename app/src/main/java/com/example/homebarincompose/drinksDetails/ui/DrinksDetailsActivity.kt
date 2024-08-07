package com.example.homebarincompose.drinksDetails.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.homebarincompose.HomeBarNavigationManager.HomeBarNavigationManager.navigateToSearchRecipe
import com.example.homebarincompose.drinksDetails.DrinksDetailsViewModel
import com.example.homebarincompose.recipesearch.model.Drinks
import com.example.homebarincompose.ui.theme.HomeBarTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DrinksDetailsActivity : ComponentActivity() {

    private val viewModel: DrinksDetailsViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val drinkId = intent.getIntExtra("DRINK_ID", 0) /*?: ""*/
        Log.d("DrinksDetailsActivity", "Received DRINK_ID: $drinkId")
        viewModel.fetchDrinkDetails(drinkId.toString())
        setContent {
            HomeBarTheme {
                val state by viewModel.viewState.collectAsState()
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("Drinks Details")
                            },
                            navigationIcon = {
                                IconButton(onClick = { navigateToSearchRecipe() }) {
                                    Icon(Icons.Filled.ArrowBack, "back", tint = Color.Black)
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    DrinksDetailsScreen(
                        state = state,
                        paddingValues = paddingValues,
                        onFavouriteClick = {drink ->
                            viewModel.toggleFavourite(drink)
                        }
                    )
                }

            }
        }
    }
}

    @Composable
    fun DrinksDetailsScreen(
     //   drinkId: String,
        state: DrinksDetailsViewState,
        paddingValues: PaddingValues,
        onFavouriteClick: (Drinks) -> Unit
    ) {
        val context = LocalContext.current
        val drink = state.drinkList.find { it.idDrink.toString() == state.idDrink }

        Log.d("DrinksDetailsScreen", "Current state: $state")
        Log.d("DrinksDetailsScreen", "Found drink: $drink")

        if (drink == null) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        } else {
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                drink.strDrink?.let { strDrink ->
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = strDrink
                    )
                }
                drink.strDrinkThumb?.let { strDrinkThumb ->
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(strDrinkThumb)
                            .build(),
                        contentDescription = "This is a drink image",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                IconButton(onClick = {onFavouriteClick(drink)}){
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Add to favourites",
                        tint = if(state.isFavourite) Color.Yellow else Color.Gray
                    )
                }
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "INSTRUCTION"
                )
                drink.strInstructions?.let { strInstruction ->
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = strInstruction
                    )
                }
            }
        }
    }


    @Preview
    @Composable
    fun DefaultPreview() {
        HomeBarTheme {

            DrinksDetailsScreen(
                state = DrinksDetailsViewState(idDrink = "", isFavourite = false ),
                paddingValues = PaddingValues(),
                onFavouriteClick = {}
            )
        }
    }
