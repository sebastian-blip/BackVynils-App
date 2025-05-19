package com.example.vinyls.repositories

import android.app.Application
import com.example.vinyls.network.NetworkServiceAdapter
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AlbumRepository(private val application: Application) {


    fun formatearFecha(fecha: String): String {
        return try {
            val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = parser.parse(fecha)
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
            formatter.timeZone = TimeZone.getTimeZone("GMT-5")
            formatter.format(date!!)
        } catch (e: Exception) {
            fecha
        }
    }


    fun crearAlbum(
        name: String,
        cover: String,
        releaseDate: String,
        description: String,
        genre: String,
        recordLabel: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val albumJson = JSONObject().apply {
            put("name", name)
            put("cover", cover)
            put("releaseDate", formatearFecha(releaseDate))
            put("description", description)
            put("genre", genre)
            put("recordLabel", recordLabel)
        }
        println("Album JSON: ${albumJson.toString(2)}")
        NetworkServiceAdapter.getInstance(application)
            .postAlbum(albumJson, onSuccess, onError)
    }
}
