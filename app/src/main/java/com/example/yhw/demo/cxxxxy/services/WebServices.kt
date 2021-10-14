package com.example.yhw.demo.cxxxxy.services

import android.content.Context
import android.graphics.BitmapFactory
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.URL
import kotlin.coroutines.coroutineContext

class WebServices(private val context: Context) {

    private val api = "https://raw.githubusercontent.com/cmmobile/NasaDataSet/main/apod.json"

    fun loadAPODList(onFinished: (List<APOD>) -> Unit, onFailed: (String) -> Unit) {
        val queue = Volley.newRequestQueue(context)
        queue.add(JsonArrayRequest(Request.Method.GET, api, null,
            { response ->
                onFinished(APOD.from(jsonArray = response))
            },
            { error ->
                onFailed(error.localizedMessage ?: error.message ?: "Error empty")
            }
        ))
    }

}