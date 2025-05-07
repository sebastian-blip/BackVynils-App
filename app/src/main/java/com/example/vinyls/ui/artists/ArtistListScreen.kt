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
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

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
        Artist("Michael Jackson", "Estados Unidos", R.drawable.michael),
        Artist("Coldplay", "Reino Unido", R.drawable.canvas),
        Artist("Beyoncé", "Estados Unidos", R.drawable.bruno_mars),
        Artist("Shakira", "Colombia", R.drawable.rosalia)
    )
    val pageSize = 6
    val totalPages = (artists.size + pageSize - 1) / pageSize
    var currentPage by remember { mutableStateOf(1) }

    val pagedArtists = artists
        .drop((currentPage - 1) * pageSize)
        .take(pageSize)

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
                .weight(1f)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 60.dp)
        ) {
            items(pagedArtists) { artist ->
                ArtistCard(
                    name = artist.name,
                    nationality = artist.nationality,
                    imageRes = artist.imageRes
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = { if (currentPage > 1) currentPage-- }
            ) {
                Text("<", color = Color.White)
            }

            (1..totalPages).forEach { page ->
                TextButton(onClick = { currentPage = page }) {
                    Text(
                        text = page.toString(),
                        color = if (page == currentPage) Color.Magenta else Color.White
                    )
                }
            }

            IconButton(
                onClick = { if (currentPage < totalPages) currentPage++ }
            ) {
                Text(">", color = Color.White)
            }
        }
    }
}