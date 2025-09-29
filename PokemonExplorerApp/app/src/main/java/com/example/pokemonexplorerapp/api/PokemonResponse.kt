package com.example.pokemonexplorerapp.api

import com.example.pokemonexplorerapp.model.Ability

data class PokemonResponse(
    val name: String,
    val height: Int,
    val abilities: List<Ability>,
    val weight: Int,
    val types: List<TypeSlot>,
    val sprites: Sprites,
    val stats: List<StatsTab>
)

data class StatsTab(
    val base_stat: Int,
    val effort: Int,
    val stat: Stat
)

data class Stat(
    val name: String,
    val url: String
)

data class TypeSlot(
    val slot: Int,
    val type: Type
)
data class Type(
    val name: String,
    val url: String
)

data class Sprites(
    val front_default: String?,
    val back_default: String?,
    val front_shiny: String?,
    val back_shiny: String?,
)