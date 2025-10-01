package com.example.pokemonexplorerapp.values

import androidx.compose.ui.graphics.Color
import com.example.pokemonexplorerapp.R
import com.example.pokemonexplorerapp.model.PokemonTypeInfo
import com.example.pokemonexplorerapp.theme.darkClr
import com.example.pokemonexplorerapp.theme.dragonClr
import com.example.pokemonexplorerapp.theme.electricClr
import com.example.pokemonexplorerapp.theme.fairyClr
import com.example.pokemonexplorerapp.theme.fireClr
import com.example.pokemonexplorerapp.theme.ghostClr
import com.example.pokemonexplorerapp.theme.grassClr
import com.example.pokemonexplorerapp.theme.psychicClr
import com.example.pokemonexplorerapp.theme.steelClr
import com.example.pokemonexplorerapp.theme.waterClr

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


val typeColorPalette: Map<String, Color> = mapOf(
    "fire" to fireClr,
    "water" to waterClr,
    "grass" to grassClr,
    "electric" to electricClr,
    "dragon" to dragonClr,
    "psychic" to psychicClr,
    "ghost" to ghostClr,
    "dark" to darkClr,
    "steel" to steelClr,
    "fairy" to fairyClr,
)

val icons: Map<String, Int> = mapOf(
    "fire" to R.drawable.fire,
    "water" to R.drawable.water,
    "grass" to R.drawable.grass,
    "electric" to R.drawable.electric,
    "dragon" to R.drawable.dragon,
    "psychic" to R.drawable.psychic,
    "ghost" to R.drawable.ghost,
    "dark" to R.drawable.dark,
    "steel" to R.drawable.steel,
    "fairy" to R.drawable.fairy,
)