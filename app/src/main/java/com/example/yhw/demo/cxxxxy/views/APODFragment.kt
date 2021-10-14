package com.example.yhw.demo.cxxxxy.views

import android.content.res.Resources.getSystem
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.yhw.demo.cxxxxy.R
import com.example.yhw.demo.cxxxxy.utils.px
import com.example.yhw.demo.cxxxxy.viewModels.APODViewModel
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class APODFragment: Fragment() {

    private val viewModel: APODViewModel by activityViewModels {
        APODViewModel.Factory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_apod, container, false).apply {

        val constraintLayout = findViewById<ConstraintLayout>(R.id.constraintLayout)
        val dateTextView = findViewById<TextView>(R.id.date_textView)
        val indicatorView = findViewById<CircularProgressIndicator>(R.id.circularProgressIndicator)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val titleTextView = findViewById<TextView>(R.id.title_textView)
        val copyrightTextView = findViewById<TextView>(R.id.copyright_textView)
        val descriptionTextView = findViewById<TextView>(R.id.description_textView)

        viewModel.selectedAPOD.observe(viewLifecycleOwner) { apod ->
            if (apod != null) {
                dateTextView.text = apod.date
                titleTextView.text = apod.title
                copyrightTextView.text = apod.copyright
                descriptionTextView.text = apod.description
                lifecycleScope.launch(Dispatchers.IO) {
                    val bitmap = BitmapFactory.decodeStream(URL(apod.hdUrl).openConnection().getInputStream())
                    lifecycleScope.launch(Dispatchers.Main) {
                        imageView.setImageBitmap(bitmap)
                        indicatorView.invalidate()
                        indicatorView.visibility = View.INVISIBLE
                        with(ConstraintSet()) {
                            clone(constraintLayout)
                            clear(titleTextView.id, ConstraintSet.TOP)
                            connect(titleTextView.id, ConstraintSet.TOP, imageView.id, ConstraintSet.BOTTOM, 10.px)
                            applyTo(constraintLayout)
                        }
                    }
                }
            }
        }
    }

}