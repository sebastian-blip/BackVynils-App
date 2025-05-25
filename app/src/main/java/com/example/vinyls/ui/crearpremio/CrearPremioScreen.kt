package com.example.vinyls.ui.crearpremio

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vinyls.R
import kotlinx.coroutines.launch
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.stringResource


@Composable
fun CrearPremioScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: CrearPremioViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
    )

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    var nombreError by remember { mutableStateOf(false) }
    var descripcionError by remember { mutableStateOf(false) }
    var organizacionError by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color.Black,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            HeaderVinyls(navController)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.crear_premios),
                    color = Color(0xFFE57373),
                    style = MaterialTheme.typography.headlineMedium
                )

                Surface(
                    modifier = Modifier.size(220.dp),
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 4.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(id = R.drawable.trophy_icon),
                            contentDescription = stringResource(R.string.trofeo),
                            modifier = Modifier.size(180.dp)
                        )
                    }
                }

                OutlinedTextField(
                    value = viewModel.nombre,
                    onValueChange = {
                        viewModel.nombre = it
                        nombreError = false
                    },
                    label = { Text(stringResource(R.string.nombre)) },
                    isError = nombreError,
                    supportingText = {
                        if (nombreError) Text(stringResource(R.string.error_nombre_vacio), color = Color.Red)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        cursorColor = Color.Black
                    )
                )

                OutlinedTextField(
                    value = viewModel.descripcion,
                    onValueChange = {
                        viewModel.descripcion = it
                        descripcionError = false
                    },
                    label = { Text(stringResource(R.string.descripcion)) },
                    isError = descripcionError,
                    supportingText = {
                        if (descripcionError) Text(stringResource(R.string.error_descripcion_vacia), color = Color.Red)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        cursorColor = Color.Black
                    )
                )

                OutlinedTextField(
                    value = viewModel.organizacion,
                    onValueChange = {
                        viewModel.organizacion = it
                        organizacionError = false
                    },
                    label = { Text(stringResource(R.string.organizacion)) },
                    isError = organizacionError,
                    supportingText = {
                        if (organizacionError) Text(stringResource(R.string.error_organizacion_vacia), color = Color.Red)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        cursorColor = Color.Black
                    )
                )

                Button(
                    onClick = {
                        val nombreVacio = viewModel.nombre.isBlank()
                        val descripcionVacia = viewModel.descripcion.isBlank()
                        val organizacionVacia = viewModel.organizacion.isBlank()

                        nombreError = nombreVacio
                        descripcionError = descripcionVacia
                        organizacionError = organizacionVacia

                        if (!nombreVacio && !descripcionVacia && !organizacionVacia) {
                            viewModel.crearPremio(
                                onSuccess = {
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = context.getString(R.string.premio_creado)
                                        )
                                    }
                                },
                                onError = {
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = context.getString(R.string.error_crear_premio, it.message)
                                        )
                                    }
                                }
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.crear), color = Color.White)
                }
            }
        }
    }
}

@Composable
fun HeaderVinyls(navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_vinyls),
            contentDescription = stringResource(R.string.logo_vinyls),
            modifier = Modifier
                .height(60.dp)
                .clickable { navController.navigate("home") }
        )
        Image(
            painter = painterResource(id = R.drawable.menu_icon),
            contentDescription = stringResource(R.string.menu),
            modifier = Modifier.height(40.dp)
        )
    }
}
