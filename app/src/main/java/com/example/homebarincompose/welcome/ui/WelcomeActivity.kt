package com.example.homebarincompose.welcome.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.homebarincompose.HomeBarNavigationManager.HomeBarNavigationManager.navigateToDetailsActivity
import com.example.homebarincompose.HomeBarNavigationManager.HomeBarNavigationManager.navigateToFavouriteActivity
import com.example.homebarincompose.HomeBarNavigationManager.HomeBarNavigationManager.navigateToSearchRecipe
import com.example.homebarincompose.R
import com.example.homebarincompose.recipesearch.model.Drinks
import com.example.homebarincompose.ui.theme.HomeBarTheme
import com.example.homebarincompose.welcome.WelcomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WelcomeActivity : ComponentActivity() {
    private val viewModel: WelcomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeBarTheme {
                val state by viewModel.viewState.collectAsState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    FirstView(
                        findARecipeClicked = { navigateToSearchRecipe() },
                        favouriteClicked = { navigateToFavouriteActivity() },
                        randomDrink = state.randomDrinks,
                        onRandomDrinkClicked = viewModel::fetchRandomDrink,
                        onShowDetailsFromRandomClicked = { drinkId ->
                            navigateToDetailsActivity(
                                drinkId
                            )
                        }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstView(
    findARecipeClicked: () -> Unit,
    favouriteClicked: () -> Unit,
    randomDrink: Drinks?,
    onRandomDrinkClicked: () -> Unit,
    onShowDetailsFromRandomClicked: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "HomeBar",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues = innerPadding)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 4.dp)
                    .fillMaxWidth(),
            ) {

            }
            Text(text = " Find Your Recipe",
                letterSpacing = 5.sp,
                fontFamily = FontFamily.Cursive,
                textAlign = TextAlign.Center,
                fontSize = 48.sp,
                //   modifier = Modifier.foldIn()
            )
            Image(
                painter = painterResource(id = R.drawable.mohito_removebg_preview),
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(16f / 16f)
            )
            Button(
                onClick = findARecipeClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp),
                shape = RoundedCornerShape(corner = CornerSize(16.dp))
            ) {
                Text(
                    text = "FIND A RECIPE",
                    color = Color.Black
                )
            }
            Button(
                onClick = favouriteClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                shape = RoundedCornerShape(corner = CornerSize(16.dp))
            ) {
                Text(
                    text = "FAVOURITE",
                    color = Color.Black
                )
            }
            Button(
                onClick = onRandomDrinkClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                shape = RoundedCornerShape(corner = CornerSize(16.dp))
            ) {
                Text(
                    text = "RANDOM COCKTAIL",
                    color = Color.Black
                )
            }
            randomDrink?.let {
                if (!it.strDrinkThumb.isNullOrBlank() && !it.strDrink.isNullOrBlank() && it.idDrink != null) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                onShowDetailsFromRandomClicked.invoke(it.idDrink)
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                        //,
                        //   onClick = { onShowDetailsFromRandomClicked.invoke(it.idDrink) }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val localContext = LocalContext.current
                            AsyncImage(
                                model = ImageRequest.Builder(localContext)
                                    .data(it.strDrinkThumb)
                                    .build(),
                                contentDescription = "This is a drink image",
                                contentScale = ContentScale.FillHeight,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .clip(RoundedCornerShape(16.dp))
                            )

                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = it.strDrink,
                                    textAlign = TextAlign.Center
                                )

                        }
                    }
                }
            }
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun DefaultPreview_Light_Dark_Mode() {
    HomeBarTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            FirstView(
                findARecipeClicked = {},
                favouriteClicked = {},
                randomDrink = null,
                onRandomDrinkClicked = {},
                onShowDetailsFromRandomClicked = {}
            )
        }
    }
}
