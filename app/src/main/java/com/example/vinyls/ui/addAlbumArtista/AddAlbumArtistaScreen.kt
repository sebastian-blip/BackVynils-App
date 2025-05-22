package com.example.vinyls.ui.addAlbumArtista

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Add
import androidx.compose.foundation.clickable

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
import com.example.vinyls.viewmodels.AddAlbumArtistaViewModel

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.res.stringResource



@Composable
fun AddAlbumArtistaScreen(
    navController: NavController, viewModel: AddAlbumArtistaViewModel = viewModel(),  artistaId: Int, artistaNombre: String,
    artistaImagenUrl: String) {
    val albums by viewModel.albums.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }
    var selectedAlbum by remember { mutableStateOf<Album?>(null) }
    val context = LocalContext.current

    val message = context.getString(R.string.confirm_associate_album, selectedAlbum!!.name)
    val dialogMessage = context.getString(R.string.dialog_message_album_artist, selectedAlbum!!.name)
    val botonAceptar = stringResource(R.string.button_accept_associate_album)
    val textcancel = stringResource(R.string.button_cancel_dialog)


    if (showDialog && selectedAlbum != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    stringResource(R.string.asociar_album),
                    color = Color.White,
                    modifier = Modifier.semantics {

                        contentDescription = "Título del diálogo: Asociar Álbum"
                    }
                )
            },
            text = {
                Text(
                    message,
                    color = Color.White,
                    modifier = Modifier.semantics {
                        contentDescription = dialogMessage
                    }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.idArtista = artistaId
                        viewModel.idAlbum = selectedAlbum!!.albumId
                        viewModel.nombre = selectedAlbum!!.name
                        viewModel.cover = selectedAlbum!!.cover
                        viewModel.releaseDate = selectedAlbum!!.releaseDate
                        viewModel.descripcion = selectedAlbum!!.description
                        viewModel.genre = selectedAlbum!!.genre
                        viewModel.recordLabel = selectedAlbum!!.recordLabel

                        viewModel.asociarAlbumAArtista(
                            onSuccess = {
                                Toast.makeText(context, "Álbum agregado al artista", Toast.LENGTH_SHORT).show()
                                showDialog = false
                            },
                            onError = {
                                Toast.makeText(context, "Error al agregar el álbum", Toast.LENGTH_SHORT).show()
                                showDialog = false
                            }
                        )
                    },
                    modifier = Modifier.semantics {
                        contentDescription = botonAceptar
                    }
                ) {
                    Text(stringResource(R.string.acept), color = Color.Green)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false },
                    modifier = Modifier.semantics {
                        contentDescription = textcancel
                    }
                ) {
                    Text(stringResource(R.string.cancelar), color = Color.Red)
                }
            },
            containerColor = Color.DarkGray
        )
    }



    LaunchedEffect(Unit) {
        viewModel.loadAllAlbums()
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        HomeHeader()
        ArtistHeader(name = artistaNombre, photoUrl = artistaImagenUrl)
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))
            AddAlbumButton(navController)
        }
        Spacer(modifier = Modifier.height(8.dp))

        val filteredAlbums = if (searchQuery.isBlank()) {
            albums.take(6)
        } else {
            albums.filter { it.name.contains(searchQuery, ignoreCase = true) }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filteredAlbums) { album ->
                AlbumCard(album = album, onClick = {
                    selectedAlbum = album
                    showDialog = true
                })
            }
        }

    }
}

@Composable
fun ArtistHeader(name: String, photoUrl: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(photoUrl),
            contentDescription = "Foto artista",
            modifier = Modifier
                .semantics { contentDescription = "Foto artista" }
                .size(60.dp)
                .clip(MaterialTheme.shapes.small),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(name, style = MaterialTheme.typography.titleLarge, color = Color.White)
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text(stringResource(R.string.barra_busqueda), color = Color.Gray) },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Buscar", tint = Color.White) },
        colors = OutlinedTextFieldDefaults.colors(
            errorTextColor = Color.Black,
            errorContainerColor = Color.White,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.Black,
            cursorColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.Gray,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        modifier = modifier
            .semantics { contentDescription = "Barra Busqueda Album" }
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp)),
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
            modifier = Modifier
                .semantics { contentDescription = "Botòn Ir a Crear Album" }
                .size(32.dp)
        )
    }
}

@Composable
fun AlbumCard(album: Album, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(150.dp)
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Image(
            painter = rememberImagePainter(album.cover),
            contentDescription = album.name,
            modifier = Modifier
                .semantics { contentDescription =  album.name }
                .size(150.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = album.name,
            style = MaterialTheme.typography.titleSmall,
            maxLines = 2,
            color = Color.White
        )
        Text(
            text = album.recordLabel,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
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