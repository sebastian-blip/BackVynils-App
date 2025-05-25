package com.example.vinyls.ui.detalleartista

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinyls.repositories.ArtistaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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



    fun obtenerPremiosConFecha(
        premiosDisponibles: List<Premio>,
        performerPrizesJsonArray: JSONArray
    ): List<Premio> {
        // Crear un mapa de ID de performerPrize del artista -> premiationDate
        val premiosDelArtista = mutableMapOf<Int, String>()

        for (i in 0 until performerPrizesJsonArray.length()) {
            val obj = performerPrizesJsonArray.getJSONObject(i)
            val id = obj.getInt("id")
            val fecha = obj.getString("premiationDate")
            premiosDelArtista[id] = fecha
        }

        // Filtrar y enriquecer premios que contengan algún performerPrize del artista
        return premiosDisponibles.mapNotNull { premio ->
            // Buscar si alguno de los performerPrizes del premio está en los del artista
            val match = premio.performerPrizes.firstOrNull { it.id in premiosDelArtista }

            match?.let {
                // Usamos la fecha correspondiente al ID encontrado
                val fecha = premiosDelArtista[it.id]
                premio.copy(premiationDate = fecha)
            }
        }
    }




    fun cargarArtista(id: Int = 1) {
        cargando = true
        viewModelScope.launch {
            repository.getArtista(
                id,
                onSuccess = { json ->
                    println("JSON completo del artista:\n${json.toString(2)}")
                    nombre = json.getString("name")
                    descripcion = json.getString("description")
                    imagenUrl = json.getString("image")
                    birthDate = json.getString("birthDate")
                    albums = json.getJSONArray("albums").toAlbumList()

                    val performerPrizesArray = json.getJSONArray("performerPrizes")

                    // 👇 Ejecutamos carga de premios con suspend dentro de contexto adecuado
                    viewModelScope.launch {
                        cargando = true

                        try {

                            val allPremios = obtenerPremiosDisponiblesSuspend() // <-- carga todos los premios
                            val premiosCargados = obtenerPremiosConFecha(allPremios, performerPrizesArray)
                            println(premiosCargados)// <-- filtra y enriquece

                            _premios.clear()
                            _premios.addAll(premiosCargados)
                        } catch (e: Exception) {
                            println("Error al cargar premios: ${e.message}")
                        } finally {
                            cargando = false
                        }
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

    data class PerformerPrize(
        val id: Int,
        val premiationDate: String
    )

    data class Premio(
        val id: Int,
        val name: String,
        val organization: String,
        val description: String,
        val premiationDate: String? = null,
        val performerPrizes: List<PerformerPrize> = emptyList())

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

    suspend fun obtenerPremiosDisponiblesSuspend(): List<Premio> = withContext(Dispatchers.IO) {
        val premiosArray = repository.getPremiosSuspend()
        val premiosTemp = mutableListOf<Premio>()

        for (i in 0 until premiosArray.length()) {
            val premioJson = premiosArray.getJSONObject(i)

            // Obtener performerPrizes
            val performerPrizesJsonArray = premioJson.optJSONArray("performerPrizes") ?: JSONArray()
            val performerPrizes = mutableListOf<PerformerPrize>()
            for (j in 0 until performerPrizesJsonArray.length()) {
                val prizeJson = performerPrizesJsonArray.getJSONObject(j)
                val performerPrize = PerformerPrize(
                    id = prizeJson.getInt("id"),
                    premiationDate = prizeJson.getString("premiationDate")
                )
                performerPrizes.add(performerPrize)
            }

            // Construir el premio
            val premio = Premio(
                id = premioJson.getInt("id"),
                name = premioJson.getString("name"),
                organization = premioJson.getString("organization"),
                description = premioJson.getString("description"),
                performerPrizes = performerPrizes
            )

            premiosTemp.add(premio)
        }

        premiosTemp
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


