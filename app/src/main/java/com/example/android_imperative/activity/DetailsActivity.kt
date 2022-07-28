package com.example.android_imperative.activity

import android.app.AlarmManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.example.android_imperative.R
import com.example.android_imperative.adapter.TVShortAdapter
import com.example.android_imperative.adapter.TVShowAdapter
import com.example.android_imperative.databinding.ActivityDetailsBinding
import com.example.android_imperative.utils.Logger
import com.example.android_imperative.viewmodel.DetailsViewModel
import com.example.android_imperative.viewmodel.MainViewModel

class DetailsActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val TAG: String = DetailsActivity::class.simpleName!!
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        val ivDetail = binding.ivDetail

        val extras = intent.extras
        val show_id = extras!!.getLong("show_id")
        val show_img = extras.getString("show_img")
        val show_name = extras.getString("show_name")
        val show_network = extras.getString("show_network")

        viewModel.apiTVShowDetails(show_id.toInt())
        initObservers()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val imageTransitionName = extras.getString("iv_movie")
            ivDetail.transitionName = imageTransitionName
        }

        binding.tvName.text = show_name
        binding.tvType.text = show_network
        Glide.with(this).load(show_img).into(ivDetail)

        binding.ivClose.setOnClickListener {
            ActivityCompat.finishAfterTransition(this)
        }
    }

    private fun initObservers() {
        //Retrofit related
        viewModel.tvShowsDetails.observe(this) {
            refreshAdapter(it.tvShow.pictures)
            binding.tvDetails.text = it.tvShow.description
        }

        viewModel.errorMessage.observe(this) {
            Logger.e(TAG, it)
        }

        viewModel.isLoading.observe(this) {
            Logger.d(TAG, it.toString())
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