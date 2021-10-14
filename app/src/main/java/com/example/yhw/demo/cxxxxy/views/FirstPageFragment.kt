package com.example.yhw.demo.cxxxxy.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.yhw.demo.cxxxxy.R
import com.example.yhw.demo.cxxxxy.utils.push
import com.example.yhw.demo.cxxxxy.viewModels.APODViewModel
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirstPageFragment: Fragment() {

    private val viewModel: APODViewModel by activityViewModels {
        APODViewModel.Factory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_first_page, container, false).apply {

        val indicator = findViewById<CircularProgressIndicator>(R.id.progressIndicator)
        val button = findViewById<Button>(R.id.next_button)

        button.visibility = View.VISIBLE
        indicator.isVisible = false

        button.setOnClickListener {
            button.visibility = View.INVISIBLE
            indicator.isVisible = true
            viewModel.loadAPOD(context = requireContext()) {
                lifecycleScope.launch(Dispatchers.Main) {
                    findNavController().push(R.id.APODListFragment)
                }
            }
        }

    }

}