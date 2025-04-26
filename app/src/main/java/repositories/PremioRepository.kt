package com.example.vinyls.repositories

import android.app.Application
import com.example.vinyls.network.NetworkServiceAdapter
import org.json.JSONObject

class PremioRepository(private val application: Application) {

    fun crearPremio(
        nombre: String,
        descripcion: String,
        organizacion: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val premioJson = JSONObject().apply {
            put("name", nombre)
            put("description", descripcion)
            put("organization", organizacion)
        }

        NetworkServiceAdapter.getInstance(application)
            .postPremio(premioJson, onSuccess, onError)
    }
}