package com.example.pokemonbase.repositories

import com.example.pokemonbase.network.PokemonServiceApi
import com.example.pokemonbase.network.responses.PokemonResponse
import com.example.pokemonbase.util.ResponseProgress
import javax.inject.Inject

class PokemonServiceRepository @Inject constructor(
    private val pokemonServiceApi: PokemonServiceApi
) {

    suspend fun fetchPokemons(limit:Int,offset:Int):ResponseProgress<PokemonResponse>{
        val response =try {
            ResponseProgress.Success(data = pokemonServiceApi.getPokemonList(limit, offset))
        }
        catch (e:java.lang.Exception){
            ResponseProgress.Error(e.message)
        }
        return response
    }

}