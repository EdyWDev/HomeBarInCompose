package com.example.homebarincompose.recipesearch.ui

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.homebarincompose.HomeBarNavigationManager.HomeBarNavigationManager.navigateToWelcomeActivity
import com.example.homebarincompose.drinksDetails.ui.DrinksDetailsActivity
import com.example.homebarincompose.recipesearch.SearchViewModel
import com.example.homebarincompose.recipesearch.model.Drinks
import com.example.homebarincompose.recipesearch.model.TypeOfSearchEnum
import com.example.homebarincompose.ui.theme.HomeBarTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchActivity : ComponentActivity() {

    private val viewModel: SearchViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HomeBarTheme {
                val state by viewModel.viewState.collectAsState()
                Log.d("SearchActivity", "Current state: $state")

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("Search for recipe") // stringRes(...)
                            },
                            navigationIcon = {
                                IconButton(onClick = { navigateToWelcomeActivity() }) {
                                    Icon(Icons.Filled.ArrowBack, "back", tint = Color.White)
                                }
                            }
                        )
                    }
                )
                { paddingValues ->


                    SearchScreen(
                        onQueryChange = { viewModel::onSearchTextChange },
                        onSearch = { viewModel::onSearchTextChange },
                        onActiveChange = { viewModel.onToogleSearch() },
                        state = state,
                        paddingValues = paddingValues,
                        onSearchCategoryClicked = viewModel::onToogleCategoryOfSearch,
                        onDoneKeyboard = viewModel::searchForResult
                    )

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onQueryChange: () -> Unit,
    onSearch: () -> Unit,
    onActiveChange: () -> Unit,
    state: SearchViewState,
    onDoneKeyboard: (String) -> Unit,
    onSearchCategoryClicked: (TypeOfSearchEnum) -> Unit,
    paddingValues: PaddingValues
) {
    val scrollState = rememberScrollState()

    Box(
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        /*MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)*/
                        Color(0xFFA9A9A9),
                        Color(0xFFA2B9C4),
                        Color(0xFFA9A9A9),
                        Color(0xFFA2B9C4)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = "Find Your Recipe",
                letterSpacing = 1.sp,
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 40.dp)

            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FilterChip(
                    modifier = Modifier
                        .weight(0.45F)
                        .clip(MaterialTheme.shapes.small),
                    label = {
                        Text(
                            text = "Name",
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    onClick = { onSearchCategoryClicked.invoke(TypeOfSearchEnum.NAME) },
                    selected = state.selectedTypeOfSearch == TypeOfSearchEnum.NAME,
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = Color(0xFF6A7D88).copy(alpha = 0.1f),
                        labelColor = MaterialTheme.colorScheme.onBackground
                    )

                )
                FilterChip(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .weight(0.45F)
                        .clip(MaterialTheme.shapes.small),
                    label = {
                        Text(
                            text = "Ingredients",
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    onClick = { onSearchCategoryClicked.invoke(TypeOfSearchEnum.INGREDIENTS) },
                    selected = state.selectedTypeOfSearch == TypeOfSearchEnum.INGREDIENTS,
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = Color(0xFF6A7D88).copy(alpha = 0.1f),
                        labelColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            var text by remember { mutableStateOf("") }

            TextField(

                value = text,
                onValueChange = { newText ->
                    text = newText
                    onDoneKeyboard(newText)
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                //    .clip(MaterialTheme.shapes.medium),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                keyboardActions = KeyboardActions(onDone =
                {
                    onDoneKeyboard(text)
                }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Blue,
                    unfocusedIndicatorColor = Color.Gray,
                    focusedLabelColor = Color.Blue,
                    unfocusedLabelColor = Color.Gray
                )
            )
            state.drinksList.forEach { item ->
                ListOfDrinks(items = item)
            }
        }
    }
}


@Composable
fun ListOfDrinks(items: Drinks) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                val intent = Intent(context, DrinksDetailsActivity::class.java)
                intent.putExtra("DRINK_ID", items.idDrink)
                context.startActivity(intent)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            //  Spacer(modifier = Modifier.width(16.dp))
            items.strDrink?.let { strDrink ->
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = strDrink,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center
                )
            }
            //  Spacer(modifier = Modifier.height(8.dp))
            items.strDrinkThumb?.let { strDrinksThumb ->
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(strDrinksThumb)
                        .crossfade(true)
                        .build(),
                    contentDescription = "This is a drink image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(MaterialTheme.shapes.large)
                        .background(Color.Gray.copy(alpha = 0.2f))
                        .padding(4.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(uiMode = UI_MODE_NIGHT_NO)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    HomeBarTheme {
        val state = SearchViewState(
            //selectedValue = (""),
            isSearching = true,
            searchText = (""),
            selectedTypeOfSearch = TypeOfSearchEnum.NAME,
            switchStateForIngredients = false,
            switchStateForName = false,
            drinksList = emptyList()
        )

    }
}

