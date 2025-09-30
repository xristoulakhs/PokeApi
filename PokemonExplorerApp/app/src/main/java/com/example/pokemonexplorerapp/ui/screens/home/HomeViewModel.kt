package com.example.pokemonexplorerapp.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonexplorerapp.api.Pokemon
import com.example.pokemonexplorerapp.api.PokemonResponse
import com.example.pokemonexplorerapp.api.PokemonTypeResponse
import com.example.pokemonexplorerapp.api.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private var _pokemonList = MutableStateFlow<List<PokemonResponse>>(emptyList())
    val pokemonList: StateFlow<List<PokemonResponse>> = _pokemonList

    private var _pokemonNamesList = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemonNamesList: StateFlow<List<Pokemon>> = _pokemonNamesList

    private val _isLoading = MutableStateFlow(false)
    val isLoadingFlow: StateFlow<Boolean> = _isLoading

    // Fetch a single Pokémon by name
    fun fetchPokemonByName(name: String) {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.api.getPokemon(name.lowercase())
                _pokemonList.value = listOf(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fetch all Pokémon of a given type
    fun fetchPokemonByType(type: String) {
        if (_isLoading.value) return
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val typeResult = RetrofitInstance.api.getPokemonByType(type.lowercase())
                _pokemonNamesList.value = typeResult.pokemon ?: emptyList()
                val pokemons = typeResult.pokemon?.take(10)?.map { p ->
                    RetrofitInstance.api.getPokemon(p.pokemon.name)
                }
                _pokemonList.value = pokemons ?: emptyList()

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun reset() {
        _pokemonList.value = emptyList()
        _isLoading.value = false
        _pokemonNamesList.value = emptyList()
    }

    fun loadMorePokemon(visibleCount: Int, batchSize: Int = 10) {
        if (_isLoading.value) return
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val nextBatchNames = _pokemonNamesList.value.drop(visibleCount).take(batchSize)
                val nextBatch = nextBatchNames.map { p ->
                    RetrofitInstance.api.getPokemon(p.pokemon.name)
                }

                _pokemonList.value = _pokemonList.value + nextBatch
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

}
