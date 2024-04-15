package com.example.hobbyapps.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.hobbyapps.R
import com.example.hobbyapps.databinding.FragmentDogListBinding
import com.example.hobbyapps.viewmodel.ListViewModelDogs

class DogListFragment : Fragment() {
    private lateinit var viewModel: ListViewModelDogs
    private val dogsListAdapter = DogListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dog_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListViewModelDogs::class.java)
        viewModel.refresh()

        val recView = view?.findViewById<RecyclerView>(R.id.recView)
        val progressLoad = view?.findViewById<ProgressBar>(R.id.progressLoad)
        recView?.layoutManager = LinearLayoutManager(context)
        recView?.adapter = dogsListAdapter

        val refreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.refreshLayout)
        val txtError = view.findViewById<TextView>(R.id.txtError)
        refreshLayout.setOnRefreshListener {
            recView?.visibility = View.GONE
            txtError?.visibility = View.GONE
            progressLoad?.visibility = View.VISIBLE
            viewModel.refresh()
            refreshLayout.isRefreshing = false
        }
        observeViewModel()
    }

    fun observeViewModel() {
        // ketika observation
        viewModel.dogsLD.observe(viewLifecycleOwner, Observer {
            dogsListAdapter.updateDogsList(it)
        })
        // ketika terjadi error
        viewModel.dogsLoadErrorLD.observe(viewLifecycleOwner, Observer {
            val txtError = view?.findViewById<TextView>(R.id.txtError)
            if(it == true) {
                txtError?.visibility = View.VISIBLE
            } else {
                txtError?.visibility = View.GONE
            }
        })
        // ketika proses data
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            val recView = view?.findViewById<RecyclerView>(R.id.recView)
            val progressLoad = view?.findViewById<ProgressBar>(R.id.progressLoad)
            if(it == true) {
                recView?.visibility = View.GONE
                progressLoad?.visibility = View.VISIBLE
            } else {
                recView?.visibility = View.VISIBLE
                progressLoad?.visibility = View.GONE
            }
        })

    }
}