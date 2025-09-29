package com.example.pokemonexplorerapp.ui.screens

import android.R.attr.contentDescription
import android.R.attr.name
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.pokemonexplorerapp.R
import com.example.pokemonexplorerapp.api.PokemonResponse
import com.example.pokemonexplorerapp.api.PokemonTypeResponse
import com.example.pokemonexplorerapp.theme.lightYellow
import com.example.pokemonexplorerapp.ui.screens.home.HomeViewModel
import java.lang.ProcessBuilder.Redirect.to


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(
    navController: NavController,
    homeViewModel: HomeViewModel,
    searchContext: String?
) {
    val pokemon by homeViewModel.pokemon.collectAsState()
    val types by homeViewModel.type.collectAsState()

    // Compute results
    val hasResults = pokemon != null || types.isNotEmpty()
    val isLoading = pokemon == null && types.isEmpty() // show spinner if nothing loaded yet

    val itemsList: List<Any> = when (searchContext) {
        "type" -> types // List<PokemonTypeResponse>
        "name" -> pokemon?.let { listOf(it) } ?: emptyList() // wrap single PokemonResponse
        else -> emptyList()
    }

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
        }
        // No results error
        else if (!hasResults) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "There are no pokemon with this name or type.",
                    style = TextStyle(
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )
                )
            }
        }
        // Show results grid
        else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 10.dp),
                contentPadding = PaddingValues(vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(itemsList) { item ->

                    when (item) {
                        is PokemonResponse -> PokemonResultItem(item, typeColorPalette)
                        is PokemonTypeResponse -> PokemonResultItem(item, typeColorPalette)
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

    val primaryType = response.types.firstOrNull()?.type?.name ?: "normal"
    val typeColor = colorPalette[primaryType] ?: Color(0xFFA1887F)

    Surface(
        modifier = Modifier
            .height(135.dp)
            .clickable { /* Handle click */ },
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
                    Spacer(modifier = Modifier.height(10.dp))
                    Row() {
                        Text(response.name)
                        Row() {
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
                    Box(
                        modifier = Modifier.border(
                            BorderStroke(5.dp, lightYellow),
                            shape = RoundedCornerShape(16.dp)
                        )
                    ) {
                        AsyncImage(
                            model = response.sprites.front_default,
                            contentDescription = "pokemon image",
                            modifier = Modifier
                                .width((sWidth * 0.6).dp)
                                .height(200.dp),

                            )
                    }

                }
            }
        }
    }
}

@Composable
fun PokemonResultItem(
    response: PokemonTypeResponse, colorPalette: Map<String, Color>
) {

    Surface(
        modifier = Modifier
            .height(135.dp)
            .clickable { /* Handle click */ },
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 4.dp,
        color = Color.White,
        onClick = {

        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = "fwewgwwbw",
                contentDescription = "pokemon image",
                modifier = Modifier.size(100.dp),

                )

            Spacer(modifier = Modifier.height(10.dp))

            Text("nfjkenfkjwnkfw", style = TextStyle(fontWeight = FontWeight.Bold))
        }
    }

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                tonalElevation = 6.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("mfklemfklewfkew", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("This is a custom dialog example.")
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(onClick = { showDialog = false }) {
                        Text("Close")
                    }
                }
            }
        }
    }
}
