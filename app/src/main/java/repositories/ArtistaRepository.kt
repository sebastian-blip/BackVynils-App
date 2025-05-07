package com.example.vinyls.repositories

import android.app.Application
import com.example.vinyls.network.NetworkServiceAdapter
import org.json.JSONObject

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
}
