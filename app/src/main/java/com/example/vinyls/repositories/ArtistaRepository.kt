package com.example.vinyls.repositories

import android.app.Application
import com.example.vinyls.network.NetworkServiceAdapter
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ArtistaRepository(application: Application) {
    private val networkAdapter = NetworkServiceAdapter.getInstance(application)

    fun getArtista(
        id: Int,
        onSuccess: (JSONObject) -> Unit,
        onError: (Exception) -> Unit
    ) {
        networkAdapter.getMusico(id, onSuccess, onError)
    }

    fun getPremioPorId(id: Int, onSuccess: (JSONObject) -> Unit, onError: (Exception) -> Unit) {
        networkAdapter.getPremioById(id, onSuccess, onError)
    }

    fun getPremios(
        onSuccess: (org.json.JSONArray) -> Unit,
        onError: (Exception) -> Unit
    ) {
        networkAdapter.getAllPremios(onSuccess, onError)
    }

    fun asociarPremio(
        artistaId: Int,
        premioId: Int,
        premiationDate: String,
        onSuccess: (JSONObject) -> Unit,
        onError: (Exception) -> Unit
    ) {
        networkAdapter.asociarPremioConArtista(premioId, artistaId, premiationDate, onSuccess, onError)
    }

    suspend fun getPremioPorIdSuspend(id: Int): JSONObject {
        return suspendCancellableCoroutine { cont ->
            getPremioPorId(
                id,
                onSuccess = { cont.resume(it) },
                onError = { cont.resumeWithException(it) }
            )
        }
    }

}
