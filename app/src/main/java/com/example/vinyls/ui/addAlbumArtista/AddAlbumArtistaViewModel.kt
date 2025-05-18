package com.example.vinyls.viewmodels

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinyls.repositories.Album
import com.example.vinyls.repositories.AlbumListRepository
import com.example.vinyls.repositories.AddAlbumArtistaRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AddAlbumArtistaViewModel(application: Application) : AndroidViewModel(application) {

    private val albumListRepository = AlbumListRepository(application.applicationContext)
    private val addAlbumArtistaRepository = AddAlbumArtistaRepository(application)

    private val _allAlbums = MutableStateFlow<List<Album>>(emptyList())
    private val _searchText = MutableStateFlow("")

    val albums: StateFlow<List<Album>> = combine(_allAlbums, _searchText) { albums, query ->
        albums.filter { it.name.contains(query, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())



    fun loadAllAlbums() {
        viewModelScope.launch {
            try {
                albumListRepository.refreshCache()
                _allAlbums.value = albumListRepository.getAllCachedAlbums()
            } catch (e: Exception) {
                println("Error cargando los albumnes")

            }
        }
    }


    var idArtista by mutableStateOf(0)
    var idAlbum by mutableStateOf(0)
    var nombre by mutableStateOf("")
    var cover by mutableStateOf("")
    var releaseDate by mutableStateOf("")
    var descripcion by mutableStateOf("")
    var genre by mutableStateOf("")
    var recordLabel by mutableStateOf("")

    fun asociarAlbumAArtista(
        onSuccess: () -> Unit = {},
        onError: (Exception) -> Unit = {}
    ) {
        addAlbumArtistaRepository.addAlbum(
            idArtista,
            idAlbum,
            nombre,
            cover,
            releaseDate,
            descripcion,
            genre,
            recordLabel,
            onSuccess = {
                println("Álbum asociado exitosamente")
                onSuccess()
            },
            onError = {
                println("Error al asociar el álbum: ${it.message}")
                onError(it)
            }
        )
    }
}
