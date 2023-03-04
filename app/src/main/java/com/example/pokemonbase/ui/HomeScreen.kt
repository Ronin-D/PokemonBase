package com.example.pokemonbase.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
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

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ){
        if (pokemonList.loadState.append==LoadState.Loading){
            //todo
        }
        items(pokemonList.itemCount){ index ->
            pokemonList[index]?.let {
                PokemonItem(
                    name = it.name,
                    imageUrl = it.imageUrl,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(170.dp)
                ) {

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
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onSelect:()->Unit
){
    val defaultColor = MaterialTheme.colors.surface
    var dominantColor = remember {
        mutableStateOf(defaultColor)
    }
    Box(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor.value,
                        defaultColor
                    )
                )
            )
            .shadow(5.dp, RoundedCornerShape(5.dp))
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onSelect()
            },
        contentAlignment = Alignment.Center,
    ) {
        Column(
           horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SubcomposeAsyncImage(
                model = imageUrl,
                contentDescription = "$name image",
                modifier = Modifier
                    .fillMaxSize()
                    .size(128.dp)
                    .padding(8.dp),
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .scale(0.5f)
                    )
                },
                onSuccess = {
                    viewModel.calcDominantColor(it.result.drawable){ color->
                        dominantColor.value = color
                    }
                }
            )
            Text(text = name, modifier = Modifier.padding(8.dp))
        }
    }


}