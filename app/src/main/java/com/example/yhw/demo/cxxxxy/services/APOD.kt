package com.example.yhw.demo.cxxxxy.services

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.File
import java.io.FileOutputStream
import java.net.URL

data class APOD(
    val description: String,
    val copyright: String,
    val title: String,
    val url: String,
    val apodSite: String,
    val date: String,
    val mediaType: String,
    val hdUrl: String
) {

    companion object {
        fun from(jsonArray: JSONArray): List<APOD> {
            val arrayList = arrayListOf<APOD>()
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                arrayList.add(APOD(
                    description = jsonObject.getString("description"),
                    copyright = jsonObject.getString("copyright"),
                    title = jsonObject.getString("title"),
                    url = jsonObject.getString("url"),
                    apodSite = jsonObject.getString("apod_site"),
                    date = jsonObject.getString("date"),
                    mediaType = jsonObject.getString("media_type"),
                    hdUrl = jsonObject.getString("hdurl")
                ))
            }
            return arrayList
        }
    }

}