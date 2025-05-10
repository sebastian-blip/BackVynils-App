package com.example.vinyls.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import org.json.JSONArray
import android.util.Log
import com.android.volley.toolbox.JsonArrayRequest

class NetworkServiceAdapter private constructor(context: Context) {

    companion object {
        const val BASE_URL = "http://10.0.2.2:3000/"
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

    fun getMusico(
        id: Int,
        onSuccess: (JSONObject) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val url = "${BASE_URL}musicians/$id"
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response -> onSuccess(response) },
            { error -> onError(error) }
        )

        requestQueue.add(request)
    }

    fun getPremioById(
        premioId: Int,
        onSuccess: (JSONObject) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val url = "${BASE_URL}prizes/$premioId"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response -> onSuccess(response) },
            { error -> onError(error) }
        )
        requestQueue.add(request)
    }


    fun getAlbums(
        onSuccess: (JSONArray) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val url = BASE_URL + "albums"
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                try {
                    Log.d("GetAlbums", "Albums retrieved: $response")
                    onSuccess(response)
                } catch (e: Exception) {
                    Log.e("GetAlbums", "Error processing the response: ${e.message}")
                    onError(e)
                }
            },
            { error ->
                Log.e("GetAlbums", "Error fetching albums: ${error.message}")
                onError(Exception("Error fetching albums"))
            }
        )

        requestQueue.add(request)
    }



    // Ejemplo para otros endpoints:
    // fun getAlbums(...)
    // fun postCollector(...)
}
