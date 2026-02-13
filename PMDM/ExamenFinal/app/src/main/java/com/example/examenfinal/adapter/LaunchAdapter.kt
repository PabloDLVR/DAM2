package com.example.examenfinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.examenfinal.databinding.ItemLaunchBinding
import com.example.examenfinal.model.Launch

class LaunchAdapter(
    private var launches: List<Launch>,
    private val onFavoriteClick: (Launch) -> Unit
) : RecyclerView.Adapter<LaunchAdapter.LaunchViewHolder>() {

    class LaunchViewHolder(val binding: ItemLaunchBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        val binding = ItemLaunchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LaunchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        val launch = launches[position]

        holder.binding.tvName.text = launch.name
        holder.binding.tvDetails.text = launch.details ?: "Sin detalles"

        Glide.with(holder.itemView.context)
            .load(launch.image)
            .into(holder.binding.ivPatch)

        holder.binding.btnFavorite.setOnClickListener {
            onFavoriteClick(launch)
        }
    }

    override fun getItemCount() = launches.size

    fun updateList(newList: List<Launch>) {
        launches = newList
        notifyDataSetChanged()
    }
}

