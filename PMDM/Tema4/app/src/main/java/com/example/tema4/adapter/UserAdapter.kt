package com.example.tema4.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tema4.databinding.ItemUserBinding
import com.example.tema4.model.User

class UserAdapter(var context: Context) : RecyclerView.Adapter<UserAdapter.Myholder>() {

    private var lista: ArrayList<User>

    inner class Myholder(binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    init {
        lista = ArrayList()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Myholder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(context))
        return Myholder(binding)
    }

    override fun onBindViewHolder(
        holder: Myholder,
        position: Int
    ) {

        val user = lista[position]
        holder.binding.textoCard.text = user.email
        holder.binding.toolbarCard.title = user.firstName
        Glide.with(context).load(user.image).into(holder.binding.imagenCard)

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}