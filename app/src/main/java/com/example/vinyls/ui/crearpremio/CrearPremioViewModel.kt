package com.example.vinyls.ui.crearpremio

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.vinyls.repositories.PremioRepository

class CrearPremioViewModel(application: Application) : AndroidViewModel(application) {

    private val premioRepository = PremioRepository(application)

    var nombre by mutableStateOf("")
    var descripcion by mutableStateOf("")
    var organizacion by mutableStateOf("")

    var nombreError by mutableStateOf(false)
    var descripcionError by mutableStateOf(false)
    var organizacionError by mutableStateOf(false)

    fun crearPremio(
        onSuccess: () -> Unit = {},
        onError: (Exception) -> Unit = {}
    ) {
        nombreError = nombre.isBlank()
        descripcionError = descripcion.isBlank()
        organizacionError = organizacion.isBlank()

        if (nombreError || descripcionError || organizacionError) {
            onError(Exception("Todos los campos son obligatorios"))
            return
        }

        premioRepository.crearPremio(
            nombre,
            descripcion,
            organizacion,
            onSuccess = {
                println("Premio creado exitosamente")
                onSuccess()
            },
            onError = {
                println("Error al crear el premio: ${it.message}")
                onError(it)
            }
        )
    }
}