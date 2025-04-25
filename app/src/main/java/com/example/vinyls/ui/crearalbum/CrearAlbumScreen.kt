package com.example.vinyls.ui.crearalbum

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vinyls.R
import kotlinx.coroutines.launch

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.foundation.clickable
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import coil.compose.AsyncImage
import androidx.compose.ui.draw.clip



@Composable
fun CrearAlbumScreen() {
    var expanded by remember { mutableStateOf(false) }
    val opcionesRecordLabel = listOf("Sony Music", "EMI", "Discos Fuentes", "Elektra", "Fania Records")
    var expandedGen by remember { mutableStateOf(false) }
    val opcionesGen = listOf("Classical", "Salsa", "Rock", "Folk")

    val context = LocalContext.current
    val viewModel: CrearAlbumViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
    )

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color.Black,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ){

                Text(
                    text = "Crear Álbum",
                    color = Color(0xFFFF6B6B),
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                AsyncImage(
                    model = viewModel.cover,
                    contentDescription = "Portada del álbum",
                    modifier = Modifier
                        .size(220.dp)
                        .clip(CircleShape),
                    placeholder = painterResource(id = R.drawable.ic_upload),
                    error = painterResource(id = R.drawable.ic_upload)
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = viewModel.nombre,
                    onValueChange = { viewModel.nombre = it },
                    label = { Text("Nombre")},
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        cursorColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray
                    )
                )

                OutlinedTextField(
                    value = viewModel.cover,
                    onValueChange = { viewModel.cover = it },
                    label = { Text("URL de la portada") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        cursorColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray
                    )
                )

                OutlinedTextField(
                    value = viewModel.releaseDate,
                    onValueChange = { viewModel.releaseDate = it },
                    label = { Text("Fecha de lanzamiento (YYYY-MM-DDTHH:MM:SS-05:00)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        cursorColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray
                    )
                )

                OutlinedTextField(
                    value = viewModel.descripcion,
                    onValueChange = { viewModel.descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        cursorColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray
                    )
                )


                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = viewModel.genre,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Genero") },
                        trailingIcon = {
                            IconButton(onClick = { expandedGen = !expandedGen }) {
                                Icon(
                                    imageVector = if (expandedGen) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                    contentDescription = "Abrir menú"
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expandedGen = !expandedGen },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.White,
                            cursorColor = Color.White,
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    DropdownMenu(
                        expanded = expandedGen,
                        onDismissRequest = { expandedGen = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        opcionesGen.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    viewModel.genre = opcion
                                    expandedGen = false
                                }
                            )
                        }
                    }
                }

                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = viewModel.recordLabel,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Sello discográfico") },
                        trailingIcon = {
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(
                                    imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                    contentDescription = "Abrir menú"
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = !expanded },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.White,
                            cursorColor = Color.White,
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        opcionesRecordLabel.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    viewModel.recordLabel = opcion
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.crearAlbum(
                            onSuccess = {
                                scope.launch {
                                    snackbarHostState.showSnackbar("✅ Álbum creado exitosamente")
                                }
                            },
                            onError = {
                                scope.launch {
                                    snackbarHostState.showSnackbar("❌ Error: ${it.message}")
                                }
                            }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B6B)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Crear Álbum", color = Color.White)
                }
            }
        }
    }
}
