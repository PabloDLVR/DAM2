package com.example.listas.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listas.databinding.ItemProductoBinding
import com.example.listas.model.Producto

class AdapteProducto(var lista: ArrayList<Producto>, var contexto: Context) :
    RecyclerView.Adapter<AdapteProducto.MyHolder>() {
    //Crea un holder de la clase anidada
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyHolder {
        var binding: ItemProductoBinding = ItemProductoBinding
            .inflate(LayoutInflater.from(contexto), parent, false)
        return MyHolder(binding)
    }

    //Asociar los elementos con el holder asociado
    override fun onBindViewHolder(
        holder: MyHolder,
        position: Int
    ) {
        val producto: Producto = lista[position]
        holder.binding.textView2.text = producto.nombre
        holder.binding.botonLogin.setOnClickListener { }
        holder.binding.botonDetalles.setOnClickListener { }
    }

    //Cuantos elementos tendre que pintar
    override fun getItemCount(): Int {
        return lista.size
    }

    inner class MyHolder(var binding: ItemProductoBinding) : RecyclerView.ViewHolder(binding.root)
}