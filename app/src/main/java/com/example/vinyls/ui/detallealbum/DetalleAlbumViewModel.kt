package com.example.vinyls.ui.detallealbum

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinyls.repositories.Album
import com.example.vinyls.repositories.AlbumListRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


class DetalleAlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val albumRepository = AlbumListRepository(application)

    private val _album = MutableStateFlow<Album?>(null)
    val album: StateFlow<Album?> = _album

    private var error by mutableStateOf<String?>(null)

    // Cargar un álbum por su ID
    fun loadAlbumById(albumId: Int) {
        viewModelScope.launch {
            try {
                _album.value = albumRepository.getAlbumById(albumId)
            } catch (e: Exception) {
                error = e.message
            }
        }
    }
}
