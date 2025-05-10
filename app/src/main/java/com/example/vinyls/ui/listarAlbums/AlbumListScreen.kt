package com.example.vinyls.ui.albumlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.vinyls.R
import com.example.vinyls.repositories.Album
import com.example.vinyls.viewmodels.AlbumListViewModel

@Composable
fun AlbumListScreen(navController: NavController, viewModel: AlbumListViewModel = viewModel()) {
    val albums by viewModel.albums.collectAsState()
    val currentPage by viewModel.currentPage.collectAsState()
    val itemsPerPage = 6

    LaunchedEffect(Unit) {
        viewModel.loadAlbums()
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        // Header
        HomeHeader()
        Spacer(modifier = Modifier.height(8.dp))

        // Barra de búsqueda y botón "Agregar álbum"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SearchBar(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(8.dp))
            AddAlbumButton(navController = navController)
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Lista de álbumes
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(albums) { album ->
                AlbumCard(album, navController)
            }
        }

        // Controles de paginación
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .background(Color.Black),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val isNextEnabled = albums.size >= itemsPerPage
            val isPreviousEnabled = currentPage > 1

            IconButton(onClick = { viewModel.previousPage() }, enabled = isPreviousEnabled) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Anterior", tint = Color.White)
            }

            Text(text = "$currentPage", color = Color.White)

            IconButton(onClick = { viewModel.nextPage() }, enabled = isNextEnabled) {
                Icon(Icons.Filled.ArrowForward, contentDescription = "Siguiente", tint = Color.White)
            }
        }
    }
}

@Composable
fun HomeHeader() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_vinyls),
            contentDescription = "Logo de Vinyls",
            modifier = Modifier
                .height(90.dp)
                .padding(10.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.menu_icon),
            contentDescription = "Imágen menú",
            modifier = Modifier
                .height(80.dp)
                .padding(10.dp)
        )
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("Buscar artista", color = Color.Gray) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color.White) },
        modifier = modifier
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp)),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.White,
            focusedContainerColor = Color.Black,
            unfocusedContainerColor = Color.Black,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = Color.White
        ),
        singleLine = true,
        shape = RoundedCornerShape(20.dp)
    )
}

@Composable
fun AddAlbumButton(navController: NavController) {
    IconButton(
        onClick = {
            navController.navigate("crear_album")
        }
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Agregar álbum",
            tint = Color.Red,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun AlbumCard(album: Album, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(150.dp)
            .padding(8.dp)
            .clickable {
                navController.navigate("detalle_album/${album.albumId}")
            }
    ) {
        Image(
            painter = rememberImagePainter(album.cover),
            contentDescription = album.name,
            modifier = Modifier
                .size(150.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = album.name, style = MaterialTheme.typography.titleSmall, maxLines = 2, color = Color.White)
        Text(text = album.recordLabel, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
    }
}