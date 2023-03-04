package com.example.pokemonbase.network.responses

import com.example.pokemonbase.models.PokemonListEntity

data class PokemonResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)