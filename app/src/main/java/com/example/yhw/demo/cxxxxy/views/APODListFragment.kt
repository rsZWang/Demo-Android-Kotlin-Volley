package com.example.yhw.demo.cxxxxy.views

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yhw.demo.cxxxxy.R
import com.example.yhw.demo.cxxxxy.adapter.APODListRecyclerViewAdapter
import com.example.yhw.demo.cxxxxy.services.APOD
import com.example.yhw.demo.cxxxxy.utils.push
import com.example.yhw.demo.cxxxxy.viewModels.APODViewModel
import java.io.File

class APODListFragment: Fragment(), APODListRecyclerViewAdapter.Delegate {

    private val viewModel: APODViewModel by activityViewModels {
        APODViewModel.Factory()
    }

    private val recyclerViewAdapter: APODListRecyclerViewAdapter by lazy {
        APODListRecyclerViewAdapter(lifecycleScope, delegate = this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_apod_list, container, false).apply {
        with (findViewById<RecyclerView>(R.id.recyclerView)) {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = recyclerViewAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                val spacing = 5
                val spanCount = 4
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    val position = parent.getChildAdapterPosition(view)
                    val column = position % spanCount
                    outRect.left = column * spacing / spanCount
                    outRect.right = spacing - (column + 1) * spacing / spanCount
                    if (position >= spanCount) {
                        outRect.top = spacing
                    }
                }
            })
        }
        viewModel.apodListLiveData.observe(viewLifecycleOwner) { list ->
            if (!list.isNullOrEmpty()) {
                recyclerViewAdapter.setData(list)
            } else {
                Toast.makeText(requireContext(), "Failed to retrieve APOD list!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onSelected(apod: APOD) {
        viewModel.selectedAPOD.postValue(apod)
        findNavController().push(R.id.APODFragment)
    }

}