package com.example.android_imperative.fragment.details

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.android_imperative.R
import com.example.android_imperative.adapter.TVShortAdapter
import com.example.android_imperative.databinding.FragmentDetailsBinding
import com.example.android_imperative.fragment.BaseFragment
import com.example.android_imperative.viewmodel.DetailsViewModel


class DetailsFragment : BaseFragment(R.layout.fragment_details) {

    private lateinit var binding: FragmentDetailsBinding
    private val detailsViewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sendRequest(arguments?.get("id").toString().toInt())

        val transition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }

    private fun sendRequest(id: Int) {
        detailsViewModel.apiTVShowDetails(id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val transition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition

        binding = FragmentDetailsBinding.bind(view)

        binding.ivMovie.transitionName = arguments?.get("iv_movie").toString()

        initViews()
    }

    private fun initViews() {
        initObservers()

        arguments?.let {
            binding.apply {
                tvDetails.text = it.get("show_img").toString()
                tvName.text = it.get("show_name").toString()
                tvType.text = it.get("show_network").toString()
                Glide.with(this@DetailsFragment).load(it.get("show_img").toString()).into(ivMovie)
            }
        }

        binding.ivClose.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initObservers() {
        //Retrofit related
        detailsViewModel.tvShowsDetails.observe(viewLifecycleOwner) {
            refreshAdapter(it.tvShow.pictures)
            binding.tvDetails.text = it.tvShow.description
        }

        detailsViewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Unknown error!", Toast.LENGTH_SHORT).show()
        }

        detailsViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
            }
        }
    }

    private fun refreshAdapter(pictures: List<String>) {
        binding.rvShorts.adapter = TVShortAdapter(pictures)
    }
}