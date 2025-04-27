package com.example.vinyls.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class NetworkServiceAdapter private constructor(context: Context) {

    companion object {
        const val BASE_URL = "http://0.0.0.0:3000"
        private var instance: NetworkServiceAdapter? = null

        fun getInstance(context: Context): NetworkServiceAdapter =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context.applicationContext).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    /**
     * POST /prizes para crear un nuevo premio
     */
    fun postPremio(
        premio: JSONObject,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val request = JsonObjectRequest(
            Request.Method.POST,
            BASE_URL + "prizes",
            premio,
            { response -> onSuccess() },
            { error -> onError(error) }
        )

        requestQueue.add(request)
    }

    fun postAlbum(
        album: JSONObject,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val request = JsonObjectRequest(
            Request.Method.POST,
            BASE_URL + "albums",
            album,
            { response -> onSuccess() },
            { error -> onError(error) }
        )

        requestQueue.add(request)
    }


    // Ejemplo para otros endpoints:
    // fun getAlbums(...)
    // fun postCollector(...)
}