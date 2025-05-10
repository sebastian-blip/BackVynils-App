package com.example.vinyls.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinyls.repositories.AlbumListRepository
import com.example.vinyls.repositories.Album
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class AlbumListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AlbumListRepository(application.applicationContext)

    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums: StateFlow<List<Album>> = _albums.asStateFlow()

    private val _currentPage = MutableStateFlow(1)
    val currentPage = _currentPage.asStateFlow()

    fun loadAlbums(page: Int = _currentPage.value) {
        viewModelScope.launch {
            try {
                val result = repository.getAlbums(page)
                _albums.value = result
                _currentPage.value = page
            } catch (e: Exception) {
                // Manejo de error aquí (mostrar Snackbar, log, etc.)
            }
        }
    }

    fun nextPage() = loadAlbums(_currentPage.value + 1)
    fun previousPage() {
        if (_currentPage.value > 1) {
            loadAlbums(_currentPage.value - 1)
        }
    }

}
