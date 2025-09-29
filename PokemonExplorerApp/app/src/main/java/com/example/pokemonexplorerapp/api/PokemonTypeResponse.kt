package com.example.pokemonexplorerapp.api

data class PokemonTypeResponse (
    val names: List<Name>
)

data class Name(
    val language: Language,
    val name: String
)

data class Language(
    val name: String,
    val url: String
)