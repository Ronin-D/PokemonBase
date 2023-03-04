package com.example.pokemonbase.di

import com.example.pokemonbase.network.PokemonServiceApi
import com.example.pokemonbase.network.PokemonServicePagingSource
import com.example.pokemonbase.repositories.PokemonServiceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val POKEMON_URL = "https://pokeapi.co/api/v2/"

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providePokemonServiceApi():PokemonServiceApi{
        return Retrofit.Builder()
            .baseUrl(POKEMON_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonServiceApi::class.java)
    }

    @Provides
    @Singleton
    fun providePokemonRepository(
        api: PokemonServiceApi
    ): PokemonServiceRepository = PokemonServiceRepository(api)

    @Provides
    @Singleton
    fun providePokemonPagingService(pokemonServiceRepository: PokemonServiceRepository
    ):PokemonServicePagingSource = PokemonServicePagingSource(pokemonServiceRepository)

}

