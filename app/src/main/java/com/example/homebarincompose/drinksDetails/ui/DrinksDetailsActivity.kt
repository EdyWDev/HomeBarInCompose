package com.example.homebarincompose.drinksDetails.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.homebarincompose.HomeBarNavigationManager.HomeBarNavigationManager.navigateToSearchRecipe
import com.example.homebarincompose.drinksDetails.DrinksDetailsViewModel
import com.example.homebarincompose.recipesearch.model.Drinks
import com.example.homebarincompose.ui.theme.HomeBarTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.homebarincompose.recipesearch.model.UnitAndIngredients



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
                                    Icon(Icons.Filled.ArrowBack, "back", tint = Color.White)
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    DrinksDetailsScreen(
                        state = state,
                        paddingValues = paddingValues,
                        onFavouriteClick = { drink ->
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
    val drinkIngredients = state.listOfIngredients

    Log.d("DrinksDetailsScreen", "Current state: $state")
    Log.d("DrinksDetailsScreen", "Found drink: $drink")

    if (drink == null) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    } else {
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                //.verticalScroll(rememberScrollState())
        ) {
            item{
                drink.strDrink?.let { strDrink ->
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = strDrink
                    )
                }
            }
            item{
                drink.strDrinkThumb?.let { strDrinkThumb ->
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(strDrinkThumb)
                            .build(),
                        contentDescription = "This is a drink image",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            item{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    IconButton(onClick = { onFavouriteClick(drink) }) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Add to favourites",
                            tint = if (state.isFavourite) Color.Yellow else Color.Gray
                        )
                    }

                Spacer(modifier = Modifier.width(8.dp))
              Share(drink.strInstructions ?: "", context)

            }
            }

            item{
                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    text = "INSTRUCTION"
                )
            }
            item{
                drink.strInstructions?.let { strInstruction ->
                    Text(
                        modifier = Modifier.padding(vertical = 16.dp),
                        text = strInstruction
                    )
                }
            }
           item{
               Text(
                   modifier = Modifier.padding(vertical = 16.dp),
                   text = "INGREDIENTS"
               )
           }

            drinkIngredients.takeIf{it.isNotEmpty()}?.let { ingredients->
                    items(ingredients){ingredient ->
                        Text(
                            text = "${ingredient.unitIngredient} ${ingredient.ingredient}",
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
            }


        }

@Composable
fun Share(text: String, context: Context) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    Button(onClick = {
        startActivity(context, shareIntent, null)
    }) {
        Icon(imageVector = Icons.Default.Share, contentDescription = null)
        Text("Share", modifier = Modifier.padding(start = 8.dp))

    }
}


@Preview
@Composable
fun DefaultPreview() {
    HomeBarTheme {

        DrinksDetailsScreen(
            state = DrinksDetailsViewState(idDrink = "", isFavourite = false),
            paddingValues = PaddingValues(),
            onFavouriteClick = {}
        )
    }
}
