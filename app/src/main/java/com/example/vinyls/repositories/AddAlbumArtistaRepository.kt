package com.example.vinyls.repositories

import android.app.Application
import com.example.vinyls.network.NetworkServiceAdapter
import org.json.JSONArray
import org.json.JSONObject


class AddAlbumArtistaRepository(private val application: Application) {



    fun addAlbum(
        artistId: Int,
        albumId: Int,
        name: String,
        cover: String,
        releaseDate: String,
        description: String,
        genre: String,
        recordLabel: String,
        onSuccess: (JSONObject) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val nuevoAlbum = JSONObject().apply {
            put("id", albumId)
            put("name", name)
            put("cover", cover)
            put("releaseDate", releaseDate)
            put("description", description)
            put("genre", genre)
            put("recordLabel", recordLabel)
        }

        NetworkServiceAdapter.getInstance(application).getAlbumByArtisId(
            artistId,
            onSuccess = { response ->
                try {
                    val currentAlbums = response ?: JSONArray()

                    val updatedAlbums = JSONArray()
                    for (i in 0 until currentAlbums.length()) {
                        updatedAlbums.put(currentAlbums.getJSONObject(i))
                    }
                    updatedAlbums.put(nuevoAlbum)
                    println(updatedAlbums)
                    NetworkServiceAdapter.getInstance(application)
                        .putAlbumArtist(updatedAlbums, artistId, onSuccess, onError)

                } catch (e: Exception) {
                    onError(e)
                }
            },
            onError = onError
        )
    }


}