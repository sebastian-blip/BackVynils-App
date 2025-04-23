package com.example.vinyls.ui.crearalbum

import com.example.vinyls.ui.data.AlbumRepositoryFake
import com.example.vinyls.ui.model.Album
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State


class CrearAlbumViewModelFake(
    private val repository: AlbumRepositoryFake = AlbumRepositoryFake()
) : ViewModel() {

    private val _estado = mutableStateOf("")
    val estado: State<String> = _estado

    fun guardarAlbum(nombre: String, artista: String, biografia: String) {
        val album = Album(nombre, artista, biografia)

        viewModelScope.launch {
            try {
                repository.crearAlbum(album)
                _estado.value = "Álbum guardado correctamente"
            } catch (e: Exception) {
                _estado.value = "Error al guardar álbum"
            }
        }
    }
}
