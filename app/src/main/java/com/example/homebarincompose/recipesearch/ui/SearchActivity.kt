package com.example.homebarincompose.recipesearch.ui

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.homebarincompose.HomeBarNavigationManager.HomeBarNavigationManager.navigateToWelcomeActivity
import com.example.homebarincompose.drinksDetails.ui.DrinksDetailsActivity
import com.example.homebarincompose.recipesearch.SearchViewModel
import com.example.homebarincompose.recipesearch.SearchViewState
import com.example.homebarincompose.recipesearch.model.Drinks
import com.example.homebarincompose.recipesearch.model.TypeOfSearchEnum
import com.example.homebarincompose.recipesearch.model.UnitAndIngredients
import com.example.homebarincompose.ui.theme.HomeBarTheme
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.rememberDrawablePainter
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
                                    Icon(Icons.Filled.ArrowBack, "back", tint = Color.Black)
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


@Composable
fun Switch(innerPadding: PaddingValues) {
    LazyColumn(

        modifier = Modifier.fillMaxSize(),
        contentPadding = innerPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        item {
            val switchStateForName = remember { mutableStateOf(false) }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp),
                    text = "Search for recipe by NAME",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold
                )
                Switch(
                    checked = switchStateForName.value,
                    onCheckedChange = { switchStateForName.value = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.DarkGray,
                        uncheckedThumbColor = Color.Gray,
                        checkedTrackColor = Color.White.copy(alpha = 1.0f),
                        uncheckedTrackColor = Color.Blue.copy(alpha = 1.0f),
                    ),
                )
            }

        }
        item {
            val switchStateForIngredients = remember { mutableStateOf(false) }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp),
                    text = "Search for recipe by INGREDIENTS",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold
                )
                Switch(
                    modifier = Modifier.padding(vertical = 16.dp),
                    checked = switchStateForIngredients.value,
                    onCheckedChange = { switchStateForIngredients.value = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.DarkGray,
                        uncheckedThumbColor = Color.Gray,
                        checkedTrackColor = Color.White.copy(alpha = 1.0f),
                        uncheckedTrackColor = Color.Blue.copy(alpha = 1.0f),
                    ),
                )
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
    Column(
        Modifier
            .padding(paddingValues)
            .verticalScroll(scrollState)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FilterChip(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(0.5F),
                label = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        textAlign = TextAlign.Center,
                        text = "Name"
                    )
                },
                onClick = { onSearchCategoryClicked.invoke(TypeOfSearchEnum.NAME) },
                selected = state.selectedTypeOfSearch == TypeOfSearchEnum.NAME,
            )
            FilterChip(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(0.5F),
                label = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        textAlign = TextAlign.Center,
                        text = "Ingredients"
                    )
                },
                onClick = { onSearchCategoryClicked.invoke(TypeOfSearchEnum.INGREDIENTS) },
                selected = state.selectedTypeOfSearch == TypeOfSearchEnum.INGREDIENTS
            )
        }

        var text by remember { mutableStateOf("") }

        TextField(

            value = text,
            // tu wprowadzic zmiane
            onValueChange = { newText ->
                text = newText
                onDoneKeyboard(newText)
            },
            /*onValueChange = { text = it }*/
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
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
            )

        )

        Log.d("SearchScreen", "Drink list; ${state.drinksList}")
        state.drinksList.forEach { item ->
            ListOfDrinks(items = item)
        }
    }
}


@Composable
fun ListOfDrinks(items: Drinks) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable {
                val intent = Intent(context, DrinksDetailsActivity::class.java)
                intent.putExtra("DRINK_ID", items.idDrink)
                context.startActivity(intent)
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {


            Spacer(modifier = Modifier.width(16.dp))
            items.strDrink?.let { strDrink ->
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                    text = strDrink,
                    color = Color.Red,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic,
                )
            }
            items.strDrinkThumb?.let { strDrinksThumb ->
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(strDrinksThumb)
                        .build(),
                    contentDescription = "This is a drink image",
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
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

