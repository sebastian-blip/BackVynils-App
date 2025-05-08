package com.example.vinyls.ui.listarartistas

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinyls.network.NetworkServiceAdapter
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class ListarArtistasViewModel(application: Application) : AndroidViewModel(application) {

    private val network = NetworkServiceAdapter.getInstance(application)

    var artistas by mutableStateOf<List<Artista>>(emptyList())
    var cargando by mutableStateOf(false)

    fun cargarArtistas() {
        cargando = true
        viewModelScope.launch {
            network.getTodosLosArtistas(
                onSuccess = { jsonArray ->
                    artistas = jsonArray.toArtistaList()
                    cargando = false
                },
                onError = {
                    println("❌ Error al cargar artistas: ${it.message}")
                    cargando = false
                }
            )
        }
    }

    data class Artista(
        val id: Int,
        val name: String,
        val image: String
    )

    private fun JSONArray.toArtistaList(): List<Artista> {
        val list = mutableListOf<Artista>()
        for (i in 0 until length()) {
            val obj = getJSONObject(i)
            list.add(
                Artista(
                    id = obj.getInt("id"),
                    name = obj.getString("name"),
                    image = obj.getString("image")
                )
            )
        }
        return list
    }
}

