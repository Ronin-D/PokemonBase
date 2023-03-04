package com.example.pokemonbase.ui

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.palette.graphics.Palette
import com.example.pokemonbase.models.PokemonListEntity
import com.example.pokemonbase.network.PokemonServicePagingSource
import com.example.pokemonbase.repositories.PokemonServiceRepository
import com.example.pokemonbase.util.Constants
import com.example.pokemonbase.util.ResponseProgress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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
          initialLoadSize = Constants.PAGE_SIZE*3
      )
    ){
        pokemonServicePagingSource
  }.flow.cachedIn(viewModelScope)

    fun calcDominantColor(drawable:Drawable, onFinish:(Color)->Unit){
        viewModelScope.launch(Dispatchers.IO) {
            val bitmap = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888,true)
            Palette.from(bitmap).generate { palette ->
                palette?.dominantSwatch?.rgb?.let { colorValue ->
                    onFinish(Color(colorValue))
                }
            }
        }
    }

}