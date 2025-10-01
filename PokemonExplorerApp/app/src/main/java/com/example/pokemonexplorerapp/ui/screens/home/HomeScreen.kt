package com.example.pokemonexplorerapp.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pokemonexplorerapp.model.PokemonTypeInfo
import com.example.pokemonexplorerapp.values.pokemonTypes

@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel) {

    val pokemonTypes = pokemonTypes

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(paddingValues)
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Choose the Pokemon Type",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 25.sp)
            )
            //boxes with pokemon types
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                itemsIndexed(pokemonTypes) { index, item ->
                    PokemonTypeGridItem(pokemonTypes[index], navController, homeViewModel)
                }
            }

        }
    }
}

@Composable
fun PokemonTypeGridItem(
    item: PokemonTypeInfo,
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    Surface(
        modifier = Modifier
            .size(width = 40.dp, height = 100.dp),
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 4.dp,
        color = Color.White,
        onClick = {
            homeViewModel.fetchPokemonByType(item.pokemonType.lowercase())
            navController.navigate("results/${item.pokemonType}")
        }
    ) {
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

            Text(item.pokemonType, style = TextStyle(fontWeight = FontWeight.Bold))
        }
    }
}