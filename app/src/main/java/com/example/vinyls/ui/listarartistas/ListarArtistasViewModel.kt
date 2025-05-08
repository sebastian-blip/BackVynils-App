package com.example.vinyls.ui.listarartistas

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinyls.network.NetworkServiceAdapter
import kotlinx.coroutines.launch
import org.json.JSONArray

class ListarArtistasViewModel(application: Application) : AndroidViewModel(application) {

    private val network = NetworkServiceAdapter.getInstance(application)

    var artistas by mutableStateOf<List<Artista>>(emptyList())
    var cargando by mutableStateOf(false)

    // 🟣 Lógica de paginación
    var paginaActual by mutableStateOf(1)
    private val artistasPorPagina = 6

    val artistasPaginados: List<Artista>
        get() {
            val fromIndex = (paginaActual - 1) * artistasPorPagina
            val toIndex = minOf(fromIndex + artistasPorPagina, artistas.size)
            return if (fromIndex < toIndex) artistas.subList(fromIndex, toIndex) else emptyList()
        }

    val totalPaginas: Int
        get() = (artistas.size + artistasPorPagina - 1) / artistasPorPagina

    fun irAPagina(pagina: Int) {
        if (pagina in 1..totalPaginas) {
            paginaActual = pagina
        }
    }

    fun siguientePagina() {
        if (paginaActual < totalPaginas) {
            paginaActual++
        }
    }

    fun paginaAnterior() {
        if (paginaActual > 1) {
            paginaActual--
        }
    }

    fun cargarArtistas() {
        cargando = true
        viewModelScope.launch {
            network.getTodosLosArtistas(
                onSuccess = { jsonArray ->
                    artistas = jsonArray.toArtistaList()
                    paginaActual = 1 // reinicia a la página 1
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




