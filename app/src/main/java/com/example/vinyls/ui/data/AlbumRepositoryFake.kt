package com.example.vinyls.ui.data

import com.example.vinyls.ui.model.Album
import kotlinx.coroutines.delay


class AlbumRepositoryFake {

    private val albumes = mutableListOf<Album>()

    suspend fun crearAlbum(album: Album) {
        // Simula una espera de red
        delay(1000)
        albumes.add(album)
        println("Álbum guardado en memoria: $album")
    }

    fun obtenerAlbumes(): List<Album> = albumes
}
