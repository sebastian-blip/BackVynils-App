package com.example.vinyls.ui.crearpremio

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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vinyls.R
import kotlinx.coroutines.launch

@Composable
fun CrearPremioScreen() {
    val context = LocalContext.current
    val viewModel: CrearPremioViewModel = viewModel(
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
                modifier = Modifier.fillMaxSize()
            ) {

                Text(
                    text = "Crear Premios",
                    color = Color(0xFFE57373),
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    modifier = Modifier.size(220.dp),
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 4.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(id = R.drawable.trophy_icon),
                            contentDescription = "Trofeo",
                            modifier = Modifier.size(300.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = viewModel.nombre,
                    onValueChange = {
                        viewModel.nombre = it
                        viewModel.nombreError = false
                    },
                    label = { Text("Nombre") },
                    isError = viewModel.nombreError,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        cursorColor = Color.Black
                    )
                )
                if (viewModel.nombreError) {
                    Text(
                        text = "El nombre es obligatorio",
                        color = Color.Red,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    )
                }

                OutlinedTextField(
                    value = viewModel.descripcion,
                    onValueChange = {
                        viewModel.descripcion = it
                        viewModel.descripcionError = false
                    },// NUEVO
                    label = { Text("Descripción") },
                    isError = viewModel.descripcionError,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        cursorColor = Color.Black
                    )
                )
                if (viewModel.descripcionError) {
                    Text(
                        text = "La descripción es obligatoria",
                        color = Color.Red,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    )
                }

                OutlinedTextField(
                    value = viewModel.organizacion,
                    onValueChange = {
                        viewModel.organizacion = it
                        viewModel.organizacionError = false
                    },// NUEVO
                    label = { Text("Organización") },
                    isError = viewModel.organizacionError,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        cursorColor = Color.Black
                    )
                )
                if (viewModel.organizacionError) {
                    Text(
                        text = "La organización es obligatoria",
                        color = Color.Red,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        // NUEVO: Validaciones antes de crear
                        viewModel.nombreError = viewModel.nombre.isBlank()
                        viewModel.descripcionError = viewModel.descripcion.isBlank()
                        viewModel.organizacionError = viewModel.organizacion.isBlank()

                        if (!viewModel.nombreError && !viewModel.descripcionError && !viewModel.organizacionError) {
                            viewModel.crearPremio(
                                onSuccess = {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("🎉 Premio creado exitosamente")
                                    }
                                },
                                onError = {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("❌ Error: ${it.message}")
                                    }
                                }
                            )
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar("❗ Por favor, completa todos los campos.")
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE57373)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Crear", color = Color.White)
                }
            }
        }
    }
}
