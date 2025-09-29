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
    private var _pokemon = MutableStateFlow<PokemonResponse?>(null)
    private var _typeList = MutableStateFlow<List<PokemonTypeResponse>>(emptyList())

    var pokemon: StateFlow<PokemonResponse?> = _pokemon
    var type: StateFlow<List<PokemonTypeResponse>> = _typeList

    private var offset = 0
    private val pageSize = 10
    private var isLoading = false

    fun fetchPokemonByName(name: String) {
        viewModelScope.launch {
            try {
                val nameResult = RetrofitInstance.api.getPokemon(
                    name.lowercase()
                )
                _pokemon.value = nameResult
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchPokemonByType(type: String) {

        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            try {

                val typeResult = RetrofitInstance.api.getPokemonByType(
                    type.lowercase(), offset = offset,
                    limit = pageSize,
                )
                _typeList.value =  listOf(typeResult)
                offset += pageSize
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun reset() {
        _pokemon.value = null
        _typeList.value = emptyList()
        offset = 0
        isLoading = false
    }
}