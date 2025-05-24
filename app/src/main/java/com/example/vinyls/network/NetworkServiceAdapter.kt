package com.example.vinyls.network

import android.content.ClipDescription
import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import org.json.JSONArray
import com.android.volley.toolbox.JsonArrayRequest
import android.util.Log
import com.example.vinyls.repositories.Track
import com.android.volley.Response
import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonRequest


class JsonArrayPutRequest(
    url: String,
    private val jsonArrayBody: JSONArray?,
    listener: Response.Listener<JSONObject>,
    errorListener: Response.ErrorListener
) : JsonRequest<JSONObject>(
    Method.PUT,
    url,
    jsonArrayBody?.toString(),  // convertir el JSONArray a string para enviar en body
    listener,
    errorListener
) {
    override fun parseNetworkResponse(response: NetworkResponse): Response<JSONObject> {
        return try {
            val jsonString = String(response.data, charset(HttpHeaderParser.parseCharset(response.headers, "utf-8")))
            Response.success(JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: Exception) {
            Response.error(ParseError(e))
        }
    }

    override fun getBodyContentType(): String {
        return "application/json"
    }
}


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

    fun getTodosLosArtistas(
        onSuccess: (JSONArray) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val url = "${BASE_URL}musicians"
        val request = JsonArrayRequest(
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

    fun getAlbumById(
        albumId: Int,
        onSuccess: (JSONObject) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val url = BASE_URL + "albums/$albumId"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    Log.d("getAlbumById", "Albums retrieved: $response")
                    onSuccess(response)
                } catch (e: Exception) {
                    Log.e("getAlbumById", "Error processing the response: ${e.message}")
                    onError(e)
                }
            },
            { error ->
                onError(Exception("Error fetching album by ID"))
            }
        )
        requestQueue.add(request)
    }


    fun getAlbumByArtisId(
        artisId: Int,
        onSuccess: (JSONArray) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val url = BASE_URL + "musicians/$artisId/albums"
        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    Log.d("getAlbumById", "Albums retrieved: $response")
                    onSuccess(response)
                } catch (e: Exception) {
                    Log.e("getAlbumById", "Error processing the response: ${e.message}")
                    onError(e)
                }
            },
            { error ->
                onError(Exception("Error fetching album by ID"))
            }
        )
        requestQueue.add(request)
    }


    fun putAlbumArtist(
        albums: JSONArray,
        artistId: Int,
        onSuccess: (JSONObject) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val url = BASE_URL + "musicians/$artistId/albums"
        val request = JsonArrayPutRequest(
            url,
            albums,
            { response ->
                Log.d("putAlbumArtist", "Respuesta: $response")
                onSuccess(response)
            },
            { error ->
                Log.e("putAlbumArtist", "Error en request: ${error.message}")
                onError(Exception(error.message ?: "Error desconocido"))
            }
        )
        requestQueue.add(request)
    }

    fun getAllPremios(
        onSuccess: (JSONArray) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val url = "${BASE_URL}prizes"
        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response -> onSuccess(response) },
            { error -> onError(error) }
        )
        requestQueue.add(request)
    }






    // Ejemplo para otros endpoints:
    // fun getAlbums(...)
    // fun postCollector(...)
}
