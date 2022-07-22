package com.example.android_imperative.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_imperative.databinding.ItemTvShowBinding
import com.example.android_imperative.model.TVShow

class TVShowAdapter(
    private val items: ArrayList<TVShow>
) :
    RecyclerView.Adapter<TVShowAdapter.VH>() {


    var onClick: ((TVShow, ImageView) -> Unit)? = null

    class VH(val view: ItemTvShowBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = VH(
        ItemTvShowBinding.inflate(
            LayoutInflater.from(parent.context)
        )
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        val tvShow = items[position]
        holder.view.apply {
            Glide.with(ivMovie.context).load(tvShow.image_thumbnail_path).into(ivMovie)
            tvName.text = tvShow.name
            tvType.text = tvShow.network

            ViewCompat.setTransitionName(ivMovie, tvShow.name)
            ivMovie.setOnClickListener {
                onClick!!.invoke(tvShow, ivMovie)
            }
        }
    }

    override fun getItemCount(): Int = items.size


    @SuppressLint("NotifyDataSetChanged")
    fun setNewTVShows(tvShows: ArrayList<TVShow>) {
        items.clear()
        items.addAll(tvShows)
        notifyDataSetChanged()
    }
}