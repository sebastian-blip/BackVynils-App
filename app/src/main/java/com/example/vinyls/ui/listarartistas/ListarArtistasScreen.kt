package com.example.vinyls.ui.listarartistas

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import com.example.vinyls.R

@Composable
fun ListarArtistasScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: ListarArtistasViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
    )

    LaunchedEffect(true) {
        viewModel.cargarArtistas()
    }

    Scaffold(
        containerColor = Color.Black,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            // Header
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_vinyls),
                    contentDescription = "Logo de Vinyls",
                    modifier = Modifier
                        .height(60.dp)
                        .clickable { navController.navigate("home") }
                )
                Image(
                    painter = painterResource(id = R.drawable.menu_icon),
                    contentDescription = "Menú",
                    modifier = Modifier.height(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (viewModel.cargando) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color.White
                )
            } else {
                // Grid de artistas paginados
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(viewModel.artistasPaginados) { artista ->
                        ArtistaItem(artista = artista) {
                            navController.navigate("detalle_artista/${artista.id}")
                        }
                    }
                }

                // Paginación
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = { viewModel.paginaAnterior() },
                        enabled = viewModel.paginaActual > 1
                    ) {
                        Text("◀", color = Color.White)
                    }

                    for (i in 1..viewModel.totalPaginas) {
                        TextButton(onClick = { viewModel.irAPagina(i) }) {
                            Text(
                                text = i.toString(),
                                color = if (i == viewModel.paginaActual) Color(0xFFFFC0CB) else Color.White
                            )
                        }
                    }

                    IconButton(
                        onClick = { viewModel.siguientePagina() },
                        enabled = viewModel.paginaActual < viewModel.totalPaginas
                    ) {
                        Text("▶", color = Color.White)
                    }
                }
            }
        }
    }
}



@Composable
fun ArtistaItem(artista: ListarArtistasViewModel.Artista, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
    ) {
        Image(
            painter = rememberAsyncImagePainter(artista.image),
            contentDescription = "Imagen de ${artista.name}",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .graphicsLayer(
                    scaleX = 0.9f,
                    scaleY = 0.9f
                )
                .clip(RoundedCornerShape(12.dp))
                .padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = artista.name,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}



