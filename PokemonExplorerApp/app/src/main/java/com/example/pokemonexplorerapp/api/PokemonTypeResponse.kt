package com.example.pokemonexplorerapp.api

data class PokemonTypeResponse (
    val name: String,
    val slot: Int?,
    val pokemon: List<Pokemon>?,
)

data class Pokemon(
    val slot: Int?,
    val pokemon: PokemonItem
)

data class PokemonItem(
    val name: String,
    val url: String
)