package com.example.pokemonbase.network.responses

import com.example.pokemonbase.models.PokemonListEntity

data class Result(
    val name: String,
    val url: String
){
    fun toPokemonListEntry():PokemonListEntity{
        val pokemonIndex = if(this.url.endsWith("/")) {
            this.url.dropLast(1).takeLastWhile { it.isDigit() }
        } else {
            this.url.takeLastWhile { it.isDigit() }
        }
        val url = "https://raw.githubusercontent.com/PokeAPI" +
                "/sprites/master/sprites/pokemon/${pokemonIndex}.png"
        return PokemonListEntity(this.name,url)
    }
}