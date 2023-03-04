package com.example.pokemonbase.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.pokemonbase.models.PokemonListEntity
import com.example.pokemonbase.network.PokemonServicePagingSource
import com.example.pokemonbase.repositories.PokemonServiceRepository
import com.example.pokemonbase.util.Constants
import com.example.pokemonbase.util.ResponseProgress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
 // private val pokemonServiceRepository: PokemonServiceRepository,
  private val pokemonServicePagingSource: PokemonServicePagingSource
):ViewModel() {

    val pokemonPager = Pager(
      PagingConfig(
          pageSize = Constants.PAGE_SIZE,
          initialLoadSize = Constants.PAGE_SIZE*3,
          enablePlaceholders = true
      )
    ){
        pokemonServicePagingSource
  }.flow.cachedIn(viewModelScope)

}