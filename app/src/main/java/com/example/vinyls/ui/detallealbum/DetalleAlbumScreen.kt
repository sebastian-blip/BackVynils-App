package com.example.vinyls.ui.detallealbum

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vinyls.R
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.vinyls.repositories.Album
import com.example.vinyls.repositories.Track

@Composable
fun DetalleAlbumScreen(
    navController: NavController,
    albumId: Int,
    viewModel: DetalleAlbumViewModel = viewModel()
) {
    val albumState = viewModel.album.collectAsState()
    val album = albumState.value

    LaunchedEffect(albumId) {
        viewModel.loadAlbumById(albumId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .align(Alignment.TopCenter)
        ) {
            HomeHeader()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 100.dp, bottom = 60.dp)
            ) {
                album?.let {
                    DataCoverAlbum(album = it)
                    Spacer(modifier = Modifier.height(8.dp))
                    AlbumDetailTabs(album = it)
                }
        }

        TextButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(12.dp)
        ) {
            Text("← Volver a lista de albumes", color = Color(0xFFE57373))
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
fun DataCoverAlbum (album: Album) {
    Column {
        // Imagen del álbum
        Image(
            painter = rememberImagePainter(data = album.cover),
            contentDescription = album.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Título del álbum
        Text(
            text = album.name,
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Biografía
        Text(
            text = "Biografía",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))


        val description = album.description ?: ""
        val shortDescription = if (description.length > 400) description.take(400) + " ..." else description

        Text(
            text = shortDescription,
            color = Color.White,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun AlbumDetailTabs(album: Album) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Canciones", "Género musical", "Compañía discográfica")
    val salmonColor = Color(0xFFF08080)

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Black,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .height(4.dp),
                    color = salmonColor
                )
            }
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (selectedTabIndex == index) salmonColor else Color.White
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTabIndex) {
            0 -> TabContentTracks(canciones = album.tracks, albumCoverUrl = album.cover)
            1 -> TabContentGenre(genero = album.genre)
            2 -> TabContentRecordLabel(compania = album.recordLabel)
        }
    }
}

@Composable
fun TabContentTracks(canciones: List<Track>, albumCoverUrl: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        if (canciones.isEmpty()) {
            Text(
                text = "Este álbum no tiene canciones registradas.",
                color = Color.LightGray,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )
        } else {
            canciones.forEach { track ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(data = albumCoverUrl),
                        contentDescription = "Cover del álbum",
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = track.name,
                        color = Color.White
                    )
                }
            }
        }
    }
}


@Composable
fun TabContentGenre(genero: String) {
    Text(
        text = genero,
        color = Color.White,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun TabContentRecordLabel(compania: String) {
    Text(
        text = compania,
        color = Color.White,
        modifier = Modifier.padding(16.dp)
    )
}

