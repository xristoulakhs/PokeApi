package com.example.pokemonexplorerapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pokemonexplorerapp.R
import com.example.pokemonexplorerapp.model.PokemonTypeInfo

@Composable
fun HomeScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }

    val pokemonTypes = listOf(
        PokemonTypeInfo("Fire", R.drawable.fire),
        PokemonTypeInfo("Water", R.drawable.water),
        PokemonTypeInfo("Grass", R.drawable.grass),
        PokemonTypeInfo("Electric", R.drawable.electric),
        PokemonTypeInfo("Dragon", R.drawable.dragon),
        PokemonTypeInfo("Psychic", R.drawable.psychic),
        PokemonTypeInfo("Ghost", R.drawable.ghost),
        PokemonTypeInfo("Dark", R.drawable.dark),
        PokemonTypeInfo("Steel", R.drawable.steel),
        PokemonTypeInfo("Fairy", R.drawable.fairy),
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
                    PokemonTypeGridItem(pokemonTypes[index],navController)
                }
            }
        }
    }
}

@Composable
fun PokemonTypeGridItem(item: PokemonTypeInfo,navController: NavController) {
    Surface(
        modifier = Modifier
            .size(width = 40.dp, height = 100.dp),
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 4.dp,
        color = Color.White,
        onClick = {  navController.navigate("results")}
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = item.typeImageId),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(item.pokemonType,style = TextStyle(fontWeight = FontWeight.Bold))
        }
    }
}