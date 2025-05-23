package com.example.vinyls.ui.crearalbum

import android.app.Application
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
import androidx.compose.ui.text.font.FontStyle
import androidx.navigation.NavController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.res.stringResource



@Composable
fun CrearAlbumScreen(navController: NavController) {
    var expanded by remember { mutableStateOf(false) }
    val opcionesRecordLabel = listOf("Sony Music", "EMI", "Discos Fuentes", "Elektra", "Fania Records")
    var expandedGen by remember { mutableStateOf(false) }
    val opcionesGen = listOf("Classical", "Salsa", "Rock", "Folk")
    var nombreError by remember { mutableStateOf(false) }
    var nombreObligatorioError by remember { mutableStateOf(false) }
    var descripcionError by remember { mutableStateOf(false) }
    var descripcionObligatoriaError by remember { mutableStateOf(false) }
    var URLObligatoriaError by remember { mutableStateOf(false) }
    var URLError by remember { mutableStateOf(false) }


    val context = LocalContext.current
    val viewModel: CrearAlbumViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
    )

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    val textoNombreAlbum = stringResource(R.string.nombre)
    val textoUrlPortada = stringResource(R.string.url_portada)
    val textoFechaLanzamiento = stringResource(R.string.fecha_lanzamiento_label)
    val textDescripcion = stringResource(R.string.descripcion)
    val closeMenuGen = stringResource(R.string.menu_cerradp_gen)
    val openMenuGen =  stringResource(R.string.menu_abierto_gen)
    val closeMenuDis = stringResource(R.string.menu_cerradp_dis)
    val openMenuDis =  stringResource(R.string.menu_abierto_dis)
    val createAlbum =  stringResource(R.string.crear_album)
    val textBack =  stringResource(R.string.volver_lista_albumes)
    val textPortada = stringResource(R.string.portada_album)

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
                    text = stringResource(R.string.crear_album),
                    color = Color(0xFFFF6B6B),
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                AsyncImage(
                    model = viewModel.cover,
                    contentDescription =  textPortada,
                    modifier = Modifier
                        .size(220.dp)
                        .clip(CircleShape),
                    placeholder = painterResource(id = R.drawable.ic_upload),
                    error = painterResource(id = R.drawable.ic_upload)
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = viewModel.nombre,
                    onValueChange = {
                        viewModel.nombre = it
                        nombreObligatorioError = it.trim().isEmpty()
                        nombreError = !nombreObligatorioError && it.length > 50
                    },
                    label = { Text(stringResource(R.string.nombre))},
                    isError = nombreError || nombreObligatorioError,
                    supportingText = {
                        if (nombreObligatorioError) {
                            Text(
                                text = stringResource(R.string.campo_obligatorio),
                                color = Color(0xFFFF8C69),
                                style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else if (nombreError) {
                                Text(
                                text = stringResource(R.string.nombre_supera_longitud),
                                color = Color(0xFFFF8C69),
                                style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    },

                    modifier = Modifier
                        .semantics { contentDescription = textoNombreAlbum }
                        .fillMaxWidth(),
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
                    )
                )

                OutlinedTextField(
                    value = viewModel.cover,
                    onValueChange = {
                        viewModel.cover = it
                        URLObligatoriaError = it.trim().isEmpty()
                        URLError = !URLObligatoriaError && it.length > 500
                    },
                    label = { Text(stringResource(R.string.url_portada)) },
                    isError = URLObligatoriaError || URLError ,
                    supportingText = {
                        if (URLObligatoriaError) {
                            Text(
                                text = stringResource(R.string.campo_obligatorio),
                                color = Color(0xFFFF8C69),
                                style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else if (URLError) {
                            Text(
                                text = stringResource(R.string.url_supera_longitud),
                                color = Color(0xFFFF8C69),
                                style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    },
                    modifier = Modifier
                        .semantics { contentDescription = textoUrlPortada }
                        .fillMaxWidth(),
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
                    )
                )

                OutlinedTextField(
                    value = viewModel.releaseDate,
                    onValueChange = { viewModel.releaseDate = it },
                    label = { Text(stringResource(R.string.fecha_lanzamiento_label)) },
                    modifier = Modifier
                        .semantics { contentDescription = textoFechaLanzamiento }
                        .fillMaxWidth(),
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
                    )
                )

                OutlinedTextField(
                    value = viewModel.descripcion,
                    onValueChange = {
                        viewModel.descripcion = it
                        descripcionObligatoriaError = it.trim().isEmpty()
                        descripcionError = !descripcionObligatoriaError && it.length > 150
                    },
                    label = { Text(stringResource(R.string.descripcion)) },
                    isError = descripcionObligatoriaError || descripcionError,
                    supportingText = {
                        if (descripcionObligatoriaError) {
                            Text(
                                text = stringResource(R.string.campo_obligatorio),
                                color = Color(0xFFFF8C69),
                                style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else if (descripcionError) {
                            Text(
                                text = stringResource(R.string.descripcion_supera_longitud),
                                color = Color(0xFFFF8C69),
                                style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    },
                    modifier = Modifier
                        .semantics { contentDescription = textDescripcion }
                        .fillMaxWidth(),
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
                    )
                )


                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = viewModel.genre,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(R.string.genero)) },
                        trailingIcon = {
                            IconButton(
                                onClick = { expandedGen = !expandedGen },
                                modifier = Modifier.testTag("openDropdowGen")) {
                                Icon(
                                    imageVector = if (expandedGen) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                    contentDescription = stringResource(R.string.abrir_menu)
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expandedGen = !expandedGen }
                            .semantics {
                                contentDescription = if (expandedGen) {
                                    openMenuGen
                                } else {
                                    closeMenuGen
                                }
                            },
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

                                },
                                modifier = Modifier.testTag("menuItem_$opcion")
                            )
                        }
                    }
                }

                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = viewModel.recordLabel,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(R.string.sello_discografico)) },
                        trailingIcon = {
                            IconButton(
                                onClick = { expanded = !expanded },
                                modifier = Modifier.testTag("openDropdowRecor")) {
                                Icon(
                                    imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                    contentDescription = stringResource(R.string.abrir_menu)
                                )
                            }
                        },
                        modifier = Modifier
                            .semantics {
                                contentDescription = if (expandedGen) {
                                    openMenuDis
                                } else {
                                    closeMenuDis
                                }
                            }
                            .fillMaxWidth()
                            .clickable { expanded = !expanded },
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
                                },
                                modifier = Modifier.testTag("menuItem_$opcion")
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
                                    navController.navigate("listar_albumns")
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
                    modifier = Modifier
                    .fillMaxWidth()
                    .testTag("botonCrearAlbum")
                    .semantics { contentDescription = createAlbum }
                ) {
                    Text(stringResource(R.string.crear_album), color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.volver_lista_albumes),
                    color = Color(0xFFFF6B6B),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .semantics { contentDescription = textBack}
                        .clickable {
                            navController.navigate("listar_albumns")
                        }
                )
            }
        }
    }
}

