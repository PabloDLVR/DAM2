package com.example.listas.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listas.databinding.ItemProductoBinding
import com.example.listas.model.Producto
import com.example.listas.model.SecondActivity
import com.google.android.material.snackbar.Snackbar

class AdapteProducto(var lista: ArrayList<Producto>, var contexto: Context) :
    RecyclerView.Adapter<AdapteProducto.MyHolder>() {
    inner class MyHolder(var binding: ItemProductoBinding) : RecyclerView.ViewHolder(binding.root)

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
        holder.binding.botonDetalles.setOnClickListener {
            val intent = Intent(contexto, SecondActivity::class.java).apply {
                putExtra("Producto", producto)
            }
            contexto.startActivity(intent)
            Snackbar.make(
                holder.binding.root,
                "El precio del producto es ${producto.precio}",
                Snackbar.LENGTH_SHORT
            ).show()
        }
        holder.binding.botonLogin.setOnClickListener {
            Snackbar.make(
                holder.binding.root,
                "El precio del articulo es ${producto.precio}",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}

//Cuantos elementos tendre que pintar



