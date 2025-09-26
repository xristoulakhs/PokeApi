package com.example.pokemonexplorerapp.ui

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pokemonexplorerapp.R
import com.example.pokemonexplorerapp.model.PokemonTypeInfo
import org.w3c.dom.Text

@Composable
fun HomeScreen() {
    var searchQuery by remember { mutableStateOf("") }

    val pokemonTypes = listOf(
        PokemonTypeInfo("Fire", R.drawable.pokemonscreen),
        PokemonTypeInfo("Water", R.drawable.pokemonscreen),
        PokemonTypeInfo("Grass", R.drawable.pokemonscreen),
        PokemonTypeInfo("Electric", R.drawable.pokemonscreen),
        PokemonTypeInfo("Dragon", R.drawable.pokemonscreen),
        PokemonTypeInfo("Psychic", R.drawable.pokemonscreen),
        PokemonTypeInfo("Ghost", R.drawable.pokemonscreen),
        PokemonTypeInfo("Dark", R.drawable.pokemonscreen),
        PokemonTypeInfo("Steel", R.drawable.pokemonscreen),
        PokemonTypeInfo("Fairy", R.drawable.pokemonscreen),
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            //search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search Pokémon") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
            )

            //boxes with pokemon types
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 100.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(pokemonTypes) { index, item ->
                    PokemonTypeGridItem(pokemonTypes[index])
                }
            }
        }
    }
}

@Composable
fun PokemonTypeGridItem(item: PokemonTypeInfo) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(width = 40.dp, height = 100.dp) // fixed width & height
            .background(Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(item.pokemonType)

//            Image(
//                painter = painterResource(id = item.typeImageId),
//                contentDescription = null,
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.Crop
//            )
        }
    }
}