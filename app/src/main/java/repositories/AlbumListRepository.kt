package com.example.vinyls.repositories

import android.content.Context
import com.example.vinyls.network.NetworkServiceAdapter
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray

data class Track(
    val name: String
)

data class Album(
    val albumId: Int,
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String,
    val tracks: List<Track>
)

class AlbumListRepository(private val context: Context) {

    private val cache = mutableListOf<Album>()

    suspend fun getAlbums(page: Int, pageSize: Int = 6): List<Album> = withContext(Dispatchers.IO) {
        if (cache.isNotEmpty()) {
            return@withContext cache.drop((page - 1) * pageSize).take(pageSize)
        }

        return@withContext suspendCancellableCoroutine { continuation ->
            NetworkServiceAdapter.getInstance(context).getAlbums(
                onSuccess = { jsonArray ->
                    val allAlbums = parseAlbums(jsonArray)
                    cache.clear()
                    cache.addAll(allAlbums)
                    val pagedAlbums = allAlbums.drop((page - 1) * pageSize).take(pageSize)
                    continuation.resume(pagedAlbums, null)
                },
                onError = {
                    continuation.resumeWithException(it)
                }
            )
        }
    }

    private fun parseAlbums(jsonArray: JSONArray): List<Album> {
        return List(jsonArray.length()) { i ->
            val json = jsonArray.getJSONObject(i)
            Album(
                albumId = json.getInt("id"),
                name = json.getString("name"),
                cover = json.getString("cover"),
                releaseDate = json.getString("releaseDate"),
                description = json.getString("description"),
                genre = json.getString("genre"),
                tracks = json.getJSONArray("tracks").let { tracksArray ->
                    List(tracksArray.length()) { i ->
                        val trackJson = tracksArray.getJSONObject(i)
                        Track(
                            name = trackJson.getString("name")
                        )
                    }
                },
                recordLabel = json.getString("recordLabel")
            )
        }
    }

    suspend fun getAlbumById(albumId: Int): Album = withContext(Dispatchers.IO) {
        // Validar si existe el album en caché
        val cacheAlbum = cache.find { it.albumId == albumId }
        if (cacheAlbum != null) {
            return@withContext cacheAlbum
        }

        // Consultar el endpoint si el album no está en caché
        return@withContext suspendCancellableCoroutine { continuation ->
            NetworkServiceAdapter.getInstance(context).getAlbumById(
                albumId = albumId,
                onSuccess = { json ->
                    try {
                        val album = Album(
                            albumId = json.getInt("id"),
                            name = json.getString("name"),
                            cover = json.getString("cover"),
                            releaseDate = json.getString("releaseDate"),
                            description = json.getString("description"),
                            genre = json.getString("genre"),
                            tracks = json.getJSONArray("tracks").let { tracksArray ->
                                List(tracksArray.length()) { i ->
                                    val trackJson = tracksArray.getJSONObject(i)
                                    Track(
                                        name = trackJson.getString("name")
                                    )
                                }
                            },
                            recordLabel = json.getString("recordLabel")
                        )

                        // Agregar el album al caché si no está
                        if (cache.none { it.albumId == album.albumId }) {
                            cache.add(album)
                        }

                        continuation.resume(album, null)
                    } catch (e: Exception) {
                        continuation.resumeWithException(e)
                    }
                },
                onError = { error ->
                    continuation.resumeWithException(error)
                }
            )
        }
    }

}

