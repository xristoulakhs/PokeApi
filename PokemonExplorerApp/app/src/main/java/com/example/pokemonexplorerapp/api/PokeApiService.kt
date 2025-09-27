package com.example.pokemonexplorerapp.api

import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {
    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): PokemonResponse

    @GET("type/{type}")
    suspend fun getPokemonByType(@Path("type") type: String): PokemonTypeResponse
}