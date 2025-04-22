package com.example.vinyls.ui.crearpremio

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class CrearPremioViewModel : ViewModel() {
    var nombre by mutableStateOf("")
    var descripcion by mutableStateOf("")
    var organizacion by mutableStateOf("")

    fun crearPremio() {
        // logica para api
    }
}