package com.example.vinyls.ui.crearalbum

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.vinyls.repositories.AlbumRepository
import java.text.SimpleDateFormat
import java.util.*


class CrearAlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val albumRepository = AlbumRepository(application)

    var nombre by mutableStateOf("")
    var cover by mutableStateOf("")
    var releaseDate by mutableStateOf("")
    var descripcion by mutableStateOf("")
    var genre by mutableStateOf("")
    var recordLabel by mutableStateOf("")



    fun crearAlbum(
        onSuccess: () -> Unit = {},
        onError: (Exception) -> Unit = {}
    ) {
        if (nombre.isBlank() || cover.isBlank() || releaseDate.isBlank() || descripcion.isBlank() || genre.isBlank() || recordLabel.isBlank()) {
            onError(Exception("Todos los campos son obligatorios"))
            return
        }

        albumRepository.crearAlbum(
            nombre,
            cover,
            releaseDate,
            descripcion,
            genre,
            recordLabel,
            onSuccess = {
                println("Álbum creado exitosamente")
                onSuccess()
            },
            onError = {
                println("Error al crear el álbum: ${it.message}")
                onError(it)
            }
        )
    }
}

