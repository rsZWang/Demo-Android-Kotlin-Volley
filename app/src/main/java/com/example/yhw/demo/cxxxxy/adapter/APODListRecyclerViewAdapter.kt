package com.example.yhw.demo.cxxxxy.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yhw.demo.cxxxxy.R
import com.example.yhw.demo.cxxxxy.services.APOD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class APODListRecyclerViewAdapter(
    val coroutineScope: CoroutineScope,
    val delegate: Delegate
): RecyclerView.Adapter<APODListRecyclerViewAdapter.ViewHolder>() {

    interface Delegate {
        fun onSelected(apod: APOD)
    }

    private val apodList = ArrayList<APOD>()

    fun setData(list: List<APOD>) {
        if (apodList.isEmpty()) {
            apodList.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = apodList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_apod_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(apodList[position])

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val imageView = view.findViewById<ImageView>(R.id.imageView)
        private val titleView = view.findViewById<TextView>(R.id.title_textView)

        private var currentURL = ""

        fun bind(apod: APOD) {
            itemView.setOnClickListener(null)
            imageView.setImageBitmap(null)
            currentURL = apod.url
            coroutineScope.launch(Dispatchers.IO) {
                val bitmap = loadImageFrom(urlString = apod.url)
                coroutineScope.launch(Dispatchers.Main) {
                    if (currentURL == apod.url) {
                        imageView.setImageBitmap(bitmap)
                        titleView.text = apod.title
                        itemView.setOnClickListener {
                            delegate.onSelected(apod = apod)
                        }
                    }
                }
            }
        }

        private suspend fun loadImageFrom(urlString: String) = withContext(Dispatchers.IO) {
            var targetBitmap: Bitmap? = null
            val url = URL(urlString)
            val path = itemView.context.cacheDir.path + url.file.substringAfterLast("/")
//    Log.i("TAG", "Cache path: $path")
            val file = File(path)
            if (file.exists()) {
                targetBitmap = BitmapFactory.decodeFile(path)
            } else {
                targetBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                runCatching {
                    val fileOutputStream = FileOutputStream(file)
                    file.createNewFile()
                    val tmpBitmap = Bitmap.createScaledBitmap(
                        targetBitmap,
                        (targetBitmap.width * 0.8).toInt(),
                        (targetBitmap.height * 0.8).toInt(),
                        false
                    )
                    tmpBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream)
                    tmpBitmap.recycle()
                    fileOutputStream.close()
                }
            }
            return@withContext targetBitmap
        }
    }

}