package com.example.android_imperative.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_imperative.databinding.ItemShortDetailBinding

class TVShortAdapter(private val items: List<String>) : RecyclerView.Adapter<TVShortAdapter.VH>() {

    class VH(val view: ItemShortDetailBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = VH(
        ItemShortDetailBinding.inflate(LayoutInflater.from(parent.context))
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        Glide.with(holder.view.ivShort.context)
            .load(items[position])
            .into(holder.view.ivShort)
    }

    override fun getItemCount(): Int = items.size
}