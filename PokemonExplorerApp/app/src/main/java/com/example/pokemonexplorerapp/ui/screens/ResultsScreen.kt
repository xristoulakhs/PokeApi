package com.example.pokemonexplorerapp.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pokemonexplorerapp.api.Pokemon
import com.example.pokemonexplorerapp.api.PokemonResponse
import com.example.pokemonexplorerapp.api.PokemonTypeResponse
import com.example.pokemonexplorerapp.api.RetrofitInstance
import com.example.pokemonexplorerapp.theme.lightYellow
import com.example.pokemonexplorerapp.ui.screens.home.HomeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(
    navController: NavController,
    homeViewModel: HomeViewModel,
) {
    val pokemonNamesList by homeViewModel.pokemonNamesList.collectAsState()

    var visibleCount by remember { mutableIntStateOf(10) }
    val displayedList = homeViewModel.pokemonList.collectAsState().value.take(visibleCount)

    val isLoading by homeViewModel.isLoadingFlow.collectAsState()

    val typeColorPalette: Map<String, Color> = mapOf(
        "fire" to Color(0xFFFFB74D),
        "water" to Color(0xFF81D4FA),
        "grass" to Color(0xFF81C784),
        "electric" to Color(0xFFFFEB3B),
        "dragon" to Color(0xFF1976D2),
        "psychic" to Color(0xFFEF5350),
        "ghost" to Color(0xFF9575CD),
        "dark" to Color(0xFF90A4AE),
        "steel" to Color(0xFF0277BD),
        "fairy" to Color(0xFFF48FB1),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pokemon Results") },
                navigationIcon = {
                    IconButton(onClick = {
                        homeViewModel.reset()
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { paddingValues ->


        // Loading spinner
        if (isLoading) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .scrollable(rememberScrollState(), orientation = Orientation.Vertical)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 150.dp),
                    modifier = Modifier
                        .weight(1f) // take available space
                        .padding(horizontal = 10.dp),
                    contentPadding = PaddingValues(vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(displayedList) { item ->
                        PokemonResultItem(item, typeColorPalette)
                    }

                    if (displayedList.size < pokemonNamesList.size) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Button(
                                    onClick = {
                                        homeViewModel.loadMorePokemon(visibleCount)
                                        visibleCount += 10
                                    }
                                ) {
                                    Text("Load More")
                                }
                            }
                        }
                    }

                }
            }

        }
    }
}

@Composable
fun PokemonResultItem(
    response: PokemonResponse, colorPalette: Map<String, Color>,
) {
    var showDialog by remember { mutableStateOf(false) }
    val sHeight = LocalConfiguration.current.screenHeightDp
    val sWidth = LocalConfiguration.current.screenWidthDp

    val primaryType = response.types.firstOrNull()?.type?.name?.lowercase()
    val typeColor = colorPalette[primaryType] ?: Color(0xFFA1887F)

    Surface(
        modifier = Modifier
            .height(135.dp),
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 4.dp,
        color = Color.White,
        onClick = {
            showDialog = true
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = response.sprites.front_default,
                contentDescription = "pokemon image",
                modifier = Modifier.size(100.dp),
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                response.name.replaceFirstChar(Char::titlecase),
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
        }
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false }
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = typeColor,
                tonalElevation = 6.dp,
                border = BorderStroke(10.dp, lightYellow),
                modifier = Modifier
                    .height((sHeight * 0.6).dp)
                    .width((sWidth * 0.9).dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                )
                {
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.width((sWidth * 0.6).dp)
                    ) {
                        Text(
                            response.name.replaceFirstChar(Char::titlecase),
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 21.sp)
                        )
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                "HP",
                                style = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            )
                            Text(
                                response.stats[0].base_stat.toString(),
                                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 21.sp)
                            )
                        }
                    }
                    Surface(
                        modifier = Modifier
                            .border(
                                BorderStroke(5.dp, lightYellow),
                            )
                            .background(Color.White),
                        shadowElevation = 4.dp,
                    ) {
                        AsyncImage(
                            model = response.sprites.front_default,
                            contentDescription = "pokemon image",
                            modifier = Modifier
                                .width((sWidth * 0.7).dp)
                                .height(200.dp)
                        )
                    }

                }
            }
        }
    }
}

