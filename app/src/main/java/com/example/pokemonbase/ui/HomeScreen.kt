package com.example.pokemonbase.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.base.R
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import androidx.paging.compose.items
import com.example.pokemonbase.models.PokemonListEntity

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

    val pokemonList = viewModel.pokemonPager.collectAsLazyPagingItems()
    val context = LocalContext.current
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ){
        items(pokemonList.itemCount){ index ->
            pokemonList[index]?.let {
                PokemonItem(
                    name = it.name,
                    imageUrl = it.imageUrl,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(170.dp)
                ) {
                    Toast.makeText(context, it.name,Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}

@Composable
fun PokemonItem(
    name:String,
    imageUrl:String,
    modifier: Modifier,
    onSelect:()->Unit
){
    Box(modifier = Modifier.clickable {
        onSelect()
    }) {
        SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = "$name image",
        modifier = modifier,
        loading = {
            CircularProgressIndicator()
        }
    )
    }


}