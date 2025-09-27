package com.example.pokemonexplorerapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonexplorerapp.api.PokemonResponse
import com.example.pokemonexplorerapp.api.PokemonTypeResponse
import com.example.pokemonexplorerapp.api.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _pokemon = MutableStateFlow<PokemonResponse?>(null)
    private val _type = MutableStateFlow<PokemonTypeResponse?>(null)
    val pokemon: StateFlow<PokemonResponse?> = _pokemon
    val type: StateFlow<PokemonTypeResponse?> = _type

    fun fetchPokemonByName(name: String) {
        viewModelScope.launch {
            try {
                val nameResult = RetrofitInstance.api.getPokemon(name.lowercase())
                _pokemon.value = nameResult
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchPokemonByType(type: String) {
        viewModelScope.launch {
            try {
                
                val typeResult = RetrofitInstance.api.getPokemonByType(type.lowercase())
                _type.value = typeResult
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}