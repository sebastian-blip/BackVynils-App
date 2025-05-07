package com.example.vinyls.ui.artists

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.vinyls.R
import com.example.vinyls.ui.artists.components.ArtistCard
import com.example.vinyls.ui.homeapp.HomeHeader
import com.example.vinyls.ui.homeapp.SearchBar

data class Artist(
    val name: String,
    val nationality: String,
    val imageRes: Int
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArtistListScreen() {
    val artists = listOf(
        Artist("Bruno Mars", "Estados Unidos", R.drawable.bruno_mars),
        Artist("Dua Lipa", "Reino Unido", R.drawable.dualipa),
        Artist("Rosalía", "España", R.drawable.rosalia),
        Artist("Sabrina Carpenter", "Estados Unidos", R.drawable.shortnsweet),
        Artist("Galdivé", "Indonesia", R.drawable.afriend),
        Artist("Michael Jackson", "Estados Unidos", R.drawable.michael)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        HomeHeader()
        SearchBar()

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 60.dp)
        ) {
            items(artists) { artist ->
                ArtistCard(
                    name = artist.name,
                    nationality = artist.nationality,
                    imageRes = artist.imageRes
                )
            }
        }
    }
}