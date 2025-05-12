package com.example.vinyls.ui.detalleartista

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.vinyls.ui.detalleartista.DetalleArtistaViewModel.Album
import com.example.vinyls.ui.detalleartista.DetalleArtistaViewModel.Premio

@Composable
fun DetalleArtistaScreen(navController: NavController, artistaId: Int) {
    val context = LocalContext.current
    val viewModel: DetalleArtistaViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
    )

    val scroll = rememberScrollState()

    LaunchedEffect(artistaId) {
        viewModel.cargarArtista(artistaId)
        println("📸 URL de imagen: ${viewModel.imagenUrl}")
    }

    Scaffold(
        containerColor = Color.Black,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(scroll)
                .fillMaxSize()
        ) {
            if (viewModel.cargando) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                // Imagen
                Image(
                    painter = rememberAsyncImagePainter(viewModel.imagenUrl),
                    contentDescription = "Foto del artista",
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopCenter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Nombre
                Text(
                    viewModel.nombre,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )

                // Fecha de nacimiento como subtítulo
                val birthDateFormatted = viewModel.birthDate.split("T").firstOrNull() ?: "Fecha desconocida"
                Text(birthDateFormatted, color = Color.White)

                Spacer(modifier = Modifier.height(12.dp))

                // Biografía
                Text("Biografía", fontWeight = FontWeight.Bold, color = Color.White)
                Text(viewModel.descripcion, color = Color.White)

                Spacer(modifier = Modifier.height(24.dp))

                var selectedTabIndex by remember { mutableStateOf(0) }
                val tabs = listOf("Álbumes", "Premios")

                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = Color.Black,
                    contentColor = Color.White
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = {
                                Text(
                                    title,
                                    fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                                    color = Color.White
                                )
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                when (selectedTabIndex) {
                    0 -> {
                        viewModel.albums.forEach {
                            AlbumItem(album = it)
                        }
                    }
                    1 -> {
                        viewModel.premios.forEach {
                            PremioItem(premio = it)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                TextButton(onClick = { navController.popBackStack() }) {
                    Text("← Volver a lista de artistas", color = Color(0xFFE57373))
                }
            }
        }
    }
}

@Composable
fun AlbumItem(album: Album) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(album.name, color = Color.White, fontWeight = FontWeight.Medium)
        Text("Lanzamiento: ${album.releaseDate.split("T").firstOrNull() ?: album.releaseDate}", color = Color.Gray)
    }
}

@Composable
fun PremioItem(premio: Premio) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(premio.name, color = Color.White, fontWeight = FontWeight.Medium)
        Text("Organización: ${premio.organization}", color = Color.Gray)
        Text(premio.description, color = Color.Gray)
    }
}

