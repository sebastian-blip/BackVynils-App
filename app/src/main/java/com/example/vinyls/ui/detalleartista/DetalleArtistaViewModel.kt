package com.example.vinyls.ui.detalleartista

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinyls.repositories.ArtistaRepository
import kotlinx.coroutines.launch
import org.json.JSONArray

class DetalleArtistaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ArtistaRepository(application)

    var nombre by mutableStateOf("")
    var descripcion by mutableStateOf("")
    var imagenUrl by mutableStateOf("")
    var birthDate by mutableStateOf("")
    var albums by mutableStateOf<List<Album>>(emptyList())
    var cargando by mutableStateOf(false)
    var premiosDisponibles by mutableStateOf<List<Premio>>(emptyList())
    private val _premios = mutableStateListOf<Premio>()
    val premios: List<Premio> get() = _premios

    fun cargarArtista(id: Int = 1) {
        cargando = true
        viewModelScope.launch {
            repository.getArtista(
                id,
                onSuccess = { json ->
                    nombre = json.getString("name")
                    descripcion = json.getString("description")
                    imagenUrl = json.getString("image")
                    birthDate = json.getString("birthDate")
                    albums = json.getJSONArray("albums").toAlbumList()

                    val performerPrizesArray = json.getJSONArray("performerPrizes")
                    val premioIds = mutableListOf<Int>()

                    for (i in 0 until performerPrizesArray.length()) {
                        val premioId = performerPrizesArray.getJSONObject(i).getInt("id")
                        premioIds.add(premioId)
                    }

                    // Corrutina para cargar los premios
                    viewModelScope.launch {
                        val premiosCargados = premioIds.mapNotNull { premioId ->
                            try {
                                val premioJson = repository.getPremioPorIdSuspend(premioId)
                                Premio(
                                    id = premioJson.getInt("id"),
                                    name = premioJson.getString("name"),
                                    organization = premioJson.getString("organization"),
                                    description = premioJson.getString("description")
                                )
                            } catch (e: Exception) {
                                println("Error al cargar premio con ID $premioId: ${e.message}")
                                null
                            }
                        }
                        _premios.clear()
                        _premios.addAll(premiosCargados)
                        cargando = false
                    }
                },
                onError = {
                    println("Error al cargar artista: ${it.message}")
                    cargando = false
                }
            )
        }
    }

    // Modelos internos
    data class Album(
        val name: String,
        val releaseDate: String,
        val cover: String
    )

    data class Premio(
        val id: Int,
        val name: String,
        val organization: String,
        val description: String,
        val premiationDate: String? = null)

    // Función auxiliar para parsear álbumes
    private fun JSONArray.toAlbumList(): List<Album> {
        val list = mutableListOf<Album>()
        for (i in 0 until this.length()) {
            val obj = this.getJSONObject(i)
            list.add(
                Album(
                    name = obj.getString("name"),
                    releaseDate = obj.getString("releaseDate"),
                    cover = obj.getString("cover")
                )
            )
        }
        return list
    }

    fun obtenerPremiosDisponibles() {
        viewModelScope.launch {
            repository.getPremios(
                onSuccess = { premiosArray ->
                    val premiosTemp = mutableListOf<Premio>()
                    for (i in 0 until premiosArray.length()) {
                        val premioJson = premiosArray.getJSONObject(i)
                        val premio = Premio(
                            id = premioJson.getInt("id"),
                            name = premioJson.getString("name"),
                            organization = premioJson.getString("organization"),
                            description = premioJson.getString("description")
                        )
                        premiosTemp.add(premio)
                    }
                    premiosDisponibles = premiosTemp
                },
                onError = {
                    println("Error al cargar premios disponibles: ${it.message}")
                }
            )
        }
    }

    fun asociarPremioAlArtista(artistaId: Int, premio: Premio) {
        viewModelScope.launch {
            val premiationDate = premio.premiationDate ?: "2024-12-31T00:00:00-05:00"
            repository.asociarPremio(
                artistaId = artistaId,
                premioId = premio.id,
                premiationDate = premiationDate,
                onSuccess = {
                    val premioConFecha = premio.copy(premiationDate = premiationDate)

                    // Solo agregar al cache si no está aún
                    if (_premios.none { it.id == premioConFecha.id }) {
                        _premios.add(premioConFecha)
                    }
                },
                onError = {
                    println("Error al asociar premio: ${it.message}")
                }
            )
        }
    }

}


