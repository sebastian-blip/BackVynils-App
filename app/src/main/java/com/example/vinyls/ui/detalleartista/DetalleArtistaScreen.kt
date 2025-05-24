package com.example.vinyls.ui.detalleartista

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.vinyls.R
import com.example.vinyls.ui.detalleartista.DetalleArtistaViewModel.Album
import com.example.vinyls.ui.detalleartista.DetalleArtistaViewModel.Premio
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.contentDescription

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleArtistaScreen(navController: NavController, artistaId: Int) {
    val context = LocalContext.current
    val viewModel: DetalleArtistaViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
    )

    val scroll = rememberScrollState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showModal by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }

    var selectedPremio by remember { mutableStateOf<Premio?>(null) }

    LaunchedEffect(artistaId) {
        viewModel.cargarArtista(artistaId)
    }

    if (showModal) {
        ModalBottomSheet(
            onDismissRequest = {
                selectedPremio = null // Reiniciar el premio seleccionado si se cierra el modal
                showModal = false
            },
            sheetState = sheetState,
            containerColor = Color.DarkGray,
            contentColor = Color.White,
            scrimColor = Color.Black.copy(alpha = 0.7f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp) // Altura del modal
                    .padding(start = 12.dp, end = 12.dp, top = 2.dp, bottom = 12.dp) //Distancia entre el borde superior del modal y el titulo
            ) {
                Text(
                    text = "Agregar premios",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 16.dp)
                )

                val premios = viewModel.premiosDisponibles
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = false)
                ) {
                    items(premios) { premio ->
                        val isSelected = selectedPremio == premio
                        val backgroundColor = if (isSelected) Color.Black else Color.White
                        val contentColor = if (isSelected) Color.White else Color.Black
                        val borderColor = if (isSelected) Color.White else Color.Transparent

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clickable { selectedPremio = premio }
                                .border(
                                    width = 2.dp,
                                    color = borderColor,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .background(backgroundColor, shape = RoundedCornerShape(12.dp))
                                .padding(vertical = 14.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = premio.name,
                                color = contentColor,
                                fontSize = 15.sp, // Tamaño del  nombre de un premio
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        selectedPremio = null // <-- Reinicia el premio seleccionado si se selecciona continuar
                        showModal = false
                        showConfirmDialog = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE57373),
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.LightGray
                    ),
                    enabled = selectedPremio != null,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Continuar", fontWeight = FontWeight.Bold)
                }

            }
        }
    }

    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = {
                showConfirmDialog = false
            },
            confirmButton = {
                TextButton(onClick = {
                    showConfirmDialog = false
                    selectedPremio = null
                    // Agregar lógica para asociar el premio al artista
                }) {
                    Text("Aceptar", color = Color(0xFF4CAF50))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showConfirmDialog = false
                }) {
                    Text("Cancelar", color = Color(0xFFF44336))
                }
            },
            title = {
                Text("Asociar premio", color = Color.White)
            },
            text = {
                Text(
                    "¿Está seguro que desea agregar este premio al artista?",
                    color = Color.White
                )
            },
            containerColor = Color(0xFF1C1B1F),
            shape = RoundedCornerShape(20.dp)
        )
    }



    Scaffold(
        containerColor = Color.Black,
        topBar = {
            DetalleHeader(navController)
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scroll)
                .fillMaxSize()
        ) {
            if (viewModel.cargando) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(viewModel.imagenUrl),
                        contentDescription = "Foto del artista",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        viewModel.nombre,
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            viewModel.obtenerPremiosDisponibles()
                            showModal = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Agregar álbum",
                            tint = Color (0xC8FFEB3B),
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Agregar premio",
                            color = Color(0xC8FFEB3B),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }


                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    "Biografía",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    viewModel.descripcion,
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Justify
                )

                Spacer(modifier = Modifier.height(24.dp))

                var selectedTabIndex by remember { mutableStateOf(0) }
                val tabs = listOf("Álbumes", "Premios")

                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = Color.Black,
                    contentColor = Color.White,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier
                                .tabIndicatorOffset(tabPositions[selectedTabIndex])
                                .height(4.dp),
                            color = Color(0xFFF08080)
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (selectedTabIndex == index) Color(0xFFF08080) else Color.White
                                )
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                when (selectedTabIndex) {
                    0 -> {
                        AddAlbumButton(
                            navController = navController,
                            artistaId = artistaId,
                            artistaNombre = viewModel.nombre,
                            artistaImagenUrl = viewModel.imagenUrl
                        )

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

                TextButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(12.dp)
                ) {
                    Text("← Volver a lista de artistas", color = Color(0xFFE57373))
                }
            }
        }
    }
}

@Composable
fun AlbumItem(album: Album) {
    val fechaFormateada = album.releaseDate.split("T").firstOrNull() ?: album.releaseDate
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .semantics(mergeDescendants = true) {} // <-- esto es clave
    ) {
        Text(
            text = album.name,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "Lanzamiento: $fechaFormateada",
            color = Color.Gray
        )
    }
}



@Composable
fun PremioItem(premio: Premio) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(premio.name, color = Color.White, fontWeight = FontWeight.Medium)
        Text("Organización: ${premio.organization}", color = Color.Gray)
        Text(premio.description, color = Color.Gray)
    }
}


@Composable
fun DetalleHeader(navController: NavController) {
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
                .clickable { navController.navigate("home") }
        )

        Image(
            painter = painterResource(id = R.drawable.menu_icon),
            contentDescription = "Menú",
            modifier = Modifier
                .height(80.dp)
                .padding(10.dp)
        )
    }
}

@Composable
fun AddAlbumButton(
    navController: NavController,
    artistaId: Int,
    artistaNombre: String,
    artistaImagenUrl: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                navController.navigate("agregar_album_artista/${artistaId}/${artistaNombre}/${Uri.encode(artistaImagenUrl)}")
            }
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Agregar álbum",
            tint = Color.Red,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Agregar álbum", color = Color.White)
    }
}
