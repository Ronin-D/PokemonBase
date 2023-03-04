package com.example.pokemonbase.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokemonbase.models.PokemonListEntity
import com.example.pokemonbase.repositories.PokemonServiceRepository
import com.example.pokemonbase.util.Constants
import com.example.pokemonbase.util.ResponseProgress
import javax.inject.Inject

class PokemonServicePagingSource @Inject constructor(
    private val pokemonServiceRepository: PokemonServiceRepository
)  :PagingSource<Int,PokemonListEntity>() {
    override fun getRefreshKey(state: PagingState<Int, PokemonListEntity>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonListEntity> {
        val offset = params.key?:Constants.INITIAL_OFFSET
        val pageSize = params.loadSize
        val response  = pokemonServiceRepository.fetchPokemons(pageSize,offset)
        if (response is ResponseProgress.Success){
            val pokemonEntities = response.data!!.results
                .map{ entry ->
                    entry.toPokemonListEntry()
                }
            val nextPage = if (pokemonEntities.isEmpty()) null else offset+pageSize
            val prevPage = if (offset>pageSize) offset-pageSize else null
            return LoadResult.Page (
                data = pokemonEntities,
                nextKey = nextPage,
                prevKey = prevPage,
                itemsBefore = LoadResult.Page.COUNT_UNDEFINED,
                itemsAfter = LoadResult.Page.COUNT_UNDEFINED
            )
        }
        else {
            return LoadResult.Error(Throwable(response.message))
        }

    }

}