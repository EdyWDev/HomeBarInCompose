package com.example.homebarincompose.welcome.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
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
import kotlin.random.Random


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
            DynamicGradientBackground()
            //CocktailBackground()
            //GradientBackground()
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
                //  ImageWithBlur()

                /* Image(
                      painter = painterResource(id = R.drawable.mohito_removebg_preview),
                      contentDescription = "Cocktail Image",
                      modifier = Modifier
                          .fillMaxWidth()
                          .height(250.dp)
                          .clip(RoundedCornerShape(16.dp)),
                      contentScale = ContentScale.Crop
                  )*/


                Spacer(modifier = Modifier.height(140.dp))

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
fun CocktailWaveBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF42A5F5),
                        Color(0xFF66BB6A),
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(0f, Float.POSITIVE_INFINITY)
                )
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val wavePath = androidx.compose.ui.graphics.Path().apply {
                moveTo(0f, size.height * 0.8f)
                cubicTo(
                    size.width * 0.25f, size.height * 0.6f,
                    size.width * 0.75f, size.height * 1.0f,
                    size.width, size.height * 0.8f
                )
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                lineTo(0f, size.height)
                close()
            }
            drawPath(
                path = wavePath,
                color = Color(0xFF81C784)
            )
        }
        MistEffect()
    }
}

@Composable
fun BubbleEffect() {
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            for (i in 0..20) {
                val radius = (10..30).random().toFloat()
                val x = Random.nextFloat() * size.width
                val y = Random.nextFloat() * (size.height * 0.6f) + (size.height * 0.125f)

                drawCircle(
                    color = Color.White.copy(alpha = 0.5f),
                    radius = radius,
                    center = Offset(x, y)
                )
            }

        }
    }
}

@Composable
fun MistEffect() {
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val alpha = 0.1f
            val mistColor = Color(0xFF90A4AE)
            for (i in 0..15) {
                val x = Random.nextFloat() * size.width
                val y = Random.nextFloat() * size.height
                val radius = (20..50).random().toFloat()

                drawCircle(
                    color = mistColor.copy(alpha = alpha),
                    radius = radius,
                    center = Offset(x, y)
                )
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
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .graphicsLayer(scaleX = scale, scaleY = scale),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        ),
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
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick(drink.idDrink ?: 0) }
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .animateContentSize(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(drink.strDrinkThumb)
                    .build(),
                contentDescription = "Drink Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(180.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(2.dp, MaterialTheme.colorScheme.primary),
                )

            Spacer(modifier = Modifier.height(12.dp))

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
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Icon(Icons.Default.ArrowForward, contentDescription = "View Recipe")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "View recipe", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun CocktailBackground() {
    val color1 = Color(0xFFFFA726)
    val color2 = Color(0xFF42A5F5)
    val color3 = Color(0xFF9C27B0)
    val color4 = Color(0xFF4CAF50)

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRoundRect(
                color = color1,
                size = size.copy(width = size.width, height = size.height / 1.8f),
                topLeft = Offset(0f, 0f),
                cornerRadius = CornerRadius.Zero
            )
            drawRoundRect(
                color = color2,
                size = size.copy(width = size.width, height = size.height / 2f),
                topLeft = Offset(0f, size.height / 2f),
                cornerRadius = CornerRadius.Zero
            )
            drawCircle(
                color = color3.copy(alpha = 0.5f),
                radius = 10f,
                center = Offset(size.width * 0.2f, size.height * 0.3f)
            )
            drawCircle(
                color = color4.copy(alpha = 0.3f),
                radius = 8f,
                center = Offset(size.width * 0.8f, size.height * 0.6f)
            )

            drawCircle(
                color = color1.copy(alpha = 0.3f),
                radius = 15f,
                center = Offset(size.width * 0.5f, size.height * 0.8f)
            )

        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.2f))
        )
    }
}
@Composable
fun DynamicGradientBackground() {
    val isDarkMode = isSystemInDarkTheme()
    val colors = if (isDarkMode) {
        listOf(
            Color(0xFF2C3E50), // Ciemniejszy odcień
            Color(0xFF34495E),
            Color(0xFF7F8C8D),
            Color(0xFFBDC3C7)
        )
    } else {
        listOf(
            Color(0xFF6EC1E4), // Lazurowy błękit
            Color(0xFF4B6D7D), // Szaro-niebieski
            Color(0xFFD7C6B3),
            Color(0xFFFFC1C1) // Blady różowy
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(colors)
            )
    )
}
@Composable
fun GradientBackground() {
    val colors = listOf(
       // Blady różowy
        Color(0xFF6EC1E4), // Lazurowy błękit
        Color(0xFF4B6D7D), // Szaro-niebieski
        Color(0xFFD7C6B3),
        Color(0xFFFFC1C1),

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
fun ImageWithBlur() {
    val image = painterResource(id = R.drawable.mohito_removebg_preview)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Image(
            painter = image,
            contentDescription = "Drink Image",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )
    }
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
