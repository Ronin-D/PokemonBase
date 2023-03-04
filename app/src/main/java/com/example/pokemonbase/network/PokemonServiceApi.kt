package com.example.pokemonbase.network

import com.example.pokemonbase.models.PokemonListEntity
import com.example.pokemonbase.network.responses.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonServiceApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit:Int,
        @Query("offset") offset:Int
    ):PokemonResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(@Path("name") pokemonName:String):PokemonListEntity
}