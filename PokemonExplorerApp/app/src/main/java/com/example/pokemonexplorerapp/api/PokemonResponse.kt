package com.example.pokemonexplorerapp.api

data class PokemonResponse(
    val name: String,
    val height: Int,
    val abilities: List<String>
)