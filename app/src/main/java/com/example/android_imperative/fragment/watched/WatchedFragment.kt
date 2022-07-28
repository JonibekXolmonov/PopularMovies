package com.example.android_imperative.fragment.watched

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android_imperative.R
import com.example.android_imperative.adapter.TVShowAdapter
import com.example.android_imperative.databinding.FragmentHomeBinding
import com.example.android_imperative.fragment.BaseFragment
import com.example.android_imperative.fragment.home.HomeFragment
import com.example.android_imperative.model.TVShow
import com.example.android_imperative.utils.Logger
import com.example.android_imperative.utils.Visibility.hide
import com.example.android_imperative.utils.Visibility.show
import com.example.android_imperative.viewmodel.MainViewModel
import com.example.android_imperative.viewmodel.WatchedViewModel


class WatchedFragment : BaseFragment(R.layout.fragment_home) {

    private val viewModel: WatchedViewModel by viewModels()
    private lateinit var adapter: TVShowAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        initViews()
        sendRequest()
    }

    private fun initViews() {
        initObserves()
        refreshAdapter(ArrayList())

        binding.toolbar.title = "Watched Films"

        binding.rvPopularMovies.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun sendRequest() {
        viewModel.getTVShowsFromDB()
    }

    private fun initObserves() {
        //Retrofit related
        viewModel.tvShowsFromDB.observe(viewLifecycleOwner) {
            adapter.setNewTVShows(it)
        }
    }

    private fun refreshAdapter(items: ArrayList<TVShow>) {
        adapter = TVShowAdapter(items)
        binding.rvPopularMovies.adapter = adapter
    }
}