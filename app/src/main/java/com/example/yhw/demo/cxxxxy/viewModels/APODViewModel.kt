package com.example.yhw.demo.cxxxxy.viewModels

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.yhw.demo.cxxxxy.services.APOD
import com.example.yhw.demo.cxxxxy.services.WebServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class APODViewModel: ViewModel() {

    val apodListLiveData = MutableLiveData<List<APOD>>()
    val selectedAPOD = MutableLiveData<APOD>()

    fun loadAPOD(context: Context, onFinished: () -> Unit) {
        WebServices(context).loadAPODList(
            onFinished = { list ->
                apodListLiveData.postValue(list)
                onFinished()
            },
            onFailed = { msg ->
                viewModelScope.launch(Dispatchers.Main) {
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                }
            }
        )
    }

    class Factory: ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return APODViewModel() as T
        }
    }

}