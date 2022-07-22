package com.example.android_imperative.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_imperative.R
import com.example.android_imperative.adapter.TVShowAdapter
import com.example.android_imperative.databinding.ActivityMainBinding
import com.example.android_imperative.model.TVShow
import com.example.android_imperative.utils.Logger
import com.example.android_imperative.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : BaseActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: TVShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        initObserves()

        val glm = GridLayoutManager(this, 2)
        binding.rvPopularMovies.layoutManager = glm
        refreshAdapter(ArrayList())

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
            callDetailsActivity(tvShow, ivMovie)
        }
    }

    private fun callDetailsActivity(tvShow: TVShow, sharedImageView: ImageView) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("show_id", tvShow.id)
        intent.putExtra("show_img", tvShow.image_thumbnail_path)
        intent.putExtra("show_name", tvShow.name)
        intent.putExtra("show_network", tvShow.network)
        intent.putExtra("iv_movie", ViewCompat.getTransitionName(sharedImageView))

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            sharedImageView,
            ViewCompat.getTransitionName(sharedImageView)!!
        )
        startActivity(intent, options.toBundle())
    }

    private fun sendRequest(q: Int) {
        viewModel.apiTVShowPopular(q)
    }

    private fun initObserves() {
        //Retrofit related
        viewModel.tvShowsFromApi.observe(this) {
            adapter.setNewTVShows(it)
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

        //Room related
    }
}