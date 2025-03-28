package com.example.homebarincompose.welcome.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
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

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "HomeBar",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        ) { innerPadding ->
            GradientBackground()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Text(
                    text = " Find Your Recipe",
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        letterSpacing = 1.sp
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                   Image(
                       painter = painterResource(id = R.drawable.mohito_removebg_preview),
                       contentDescription = "Cocktail Image",
                       modifier = Modifier
                           .fillMaxWidth()
                           .height(250.dp)
                           .clip(RoundedCornerShape(16.dp)),
                       contentScale = ContentScale.Crop
                   )
                Spacer(modifier = Modifier.padding(8.dp))

                ActionButton(
                    text = "FIND A RECIPE",
                    onClick = findARecipeClicked
                )
                ActionButton(
                    text = "FAVOURITE",
                    onClick = favouriteClicked
                )
                ActionButton(
                    text = "RANDOM COCKTAIL",
                    onClick = onRandomDrinkClicked
                )

                randomDrink?.let {
                    if (!it.strDrinkThumb.isNullOrBlank() && !it.strDrink.isNullOrBlank() && it.idDrink != null) {
                        RandomDrinkCard(
                            drink = it,
                            onClick = onShowDetailsFromRandomClicked
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ActionButton(text: String, onClick: () -> Unit) {
    var isHovered = remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isHovered.value) 1.1f else 1f,
        animationSpec = tween(durationMillis = 300), label = ""
    )
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .graphicsLayer(scaleX = scale, scaleY = scale),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        ),
        // elevation = ButtonDefaults.elevation(8.dp)
        interactionSource = remember {
            MutableInteractionSource()
        }
    ) {
        Text(
            text = text,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
        )
    }
}

@Composable
fun RandomDrinkCard(drink: Drinks, onClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick(drink.idDrink ?: 0) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(16.dp),
        //  elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(drink.strDrinkThumb)
                    .build(),
                contentDescription = "Drink Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(180.dp)
                    .clip(RoundedCornerShape(16.dp)),

                )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = drink.strDrink ?: "No Name",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = { drink.idDrink?.let { onClick(it) } },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(text = "View recipe", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun GradientBackground() {
    val colors = listOf(
        Color(0xFF42A5F5),  // Jasnoniebieski
        Color(0xFF66BB6A)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(colors)
            )
    )
}

@Composable
fun WaveBackground() {
    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF5F9EA0),
            Color(0xFFB0E0E6)
        ),
        start = Offset(0f, 0f),
        end = Offset(0f, Float.POSITIVE_INFINITY)
    )
    val waveColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val wavePath = androidx.compose.ui.graphics.Path().apply {
                moveTo(0f, size.height * 0.7f)
                cubicTo(
                    size.width * 0.25f, size.height * 0.5f,
                    size.width * 0.75f, size.height * 0.9f,
                    size.width, size.height * 0.7f
                )
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            drawPath(
                path = wavePath,
                color = waveColor
                // color = Color(0x99000000)
            )
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
