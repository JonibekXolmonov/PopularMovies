package com.example.android_imperative.fragment.home

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_imperative.R
import com.example.android_imperative.adapter.TVShowAdapter
import com.example.android_imperative.databinding.FragmentHomeBinding
import com.example.android_imperative.fragment.BaseFragment
import com.example.android_imperative.model.TVShow
import com.example.android_imperative.utils.Logger
import com.example.android_imperative.utils.Visibility.hide
import com.example.android_imperative.utils.Visibility.show
import com.example.android_imperative.viewmodel.MainViewModel

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val viewModel: MainViewModel by viewModels()
    private val TAG = HomeFragment::class.simpleName!!
    private lateinit var adapter: TVShowAdapter
    private lateinit var binding: FragmentHomeBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        postponeEnterTransition()
        binding.rvPopularMovies.post { startPostponedEnterTransition() }

        initViews()
    }

    private fun initViews() {
        initObserves()

        val glm = GridLayoutManager(requireContext(), 2)
        binding.rvPopularMovies.layoutManager = glm
        refreshAdapter(ArrayList())

        binding.toolbar.title = "Popular Films"

        binding.rvPopularMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (glm.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                    val nextPage = viewModel.tvShowPopular.value!!.page + 1
                    val totalPage = viewModel.tvShowPopular.value!!.pages
                    if (nextPage <= totalPage)
                        sendRequest(nextPage)
                }
            }
        })

        binding.btnFab.setOnClickListener {
            binding.rvPopularMovies.smoothScrollToPosition(0)
        }

        sendRequest(1)
    }

    private fun refreshAdapter(items: ArrayList<TVShow>) {
        adapter = TVShowAdapter(items)
        binding.rvPopularMovies.adapter = adapter

        adapter.onClick = { tvShow, ivMovie ->
            findNavController().navigate(
                R.id.action_homeFragment_to_detailsFragment,
                bundleOf(
                    "id" to tvShow.id,
                    "show_name" to tvShow.name,
                    "show_network" to tvShow.network,
                    "show_img" to tvShow.image_thumbnail_path,
                    "iv_movie" to ViewCompat.getTransitionName(ivMovie)
                ),
                null,
                FragmentNavigatorExtras(ivMovie to ViewCompat.getTransitionName(ivMovie)!!)
            )
            viewModel.insertTVShowsToDB(tvShow)
        }
    }

    private fun sendRequest(q: Int) {
        viewModel.apiTVShowPopular(q)
    }

    private fun initObserves() {
        //Retrofit related
        viewModel.tvShowsFromApi.observe(viewLifecycleOwner) {
            adapter.setNewTVShows(it)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Error occurred!", Toast.LENGTH_SHORT).show()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.pbLoading.show()
            } else {
                binding.pbLoading.hide()
            }
        }

        //Room related
    }
}