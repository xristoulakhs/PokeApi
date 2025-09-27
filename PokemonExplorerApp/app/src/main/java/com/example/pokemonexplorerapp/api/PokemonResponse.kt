package com.example.pokemonexplorerapp.api

import com.example.pokemonexplorerapp.model.Ability

data class PokemonResponse(
    val name: String,
    val height: Int,
    val abilities: List<Ability>,
    val weight: Int,
    val types: List<String>,
    val imageUrl: String
)