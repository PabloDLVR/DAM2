package com.example.listas.dataset

import com.example.listas.model.Producto

class DataSet {
    companion object {
        var lista: ArrayList<Producto> = arrayListOf(
            Producto(
                1, "Nombre",
                12, 10.0, "Descripcion", "Categoria",
                "Imagen"
            ), Producto(
                1, "Pantalon",
                12, 140.0, "Descripcion", "Ropa",
                "https://imgs.search.brave.com/tgunem921uJEf1YEqFlKXqGr5ugDV5jksKSF-A6L3Y4/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9zdGF0/aWMuZS1zdHJhZGl2/YXJpdXMubmV0L2Fz/c2V0cy9wdWJsaWMv/YjI0Mi9jYWQ3Lzgz/ZTE0MGYyODUyMi9m/YWY4YWU3MDc3OWIv/MDQwMjAwNjYyMTAt/czEvMDQwMjAwNjYy/MTAtczEuanBnP3Rz/PTE3NTU2OTUzMjg4/NDYmdz0xMDI0"
            ), Producto(
                2, "Rompe culos",
                12, 100.0, "Puros orgasmos", "Oscar",
                "https://imgs.search.brave.com/Ief8bPlkxKGEmd4xD79F37ssSuEhTxQHcy45QN86eWU/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9tLm1l/ZGlhLWFtYXpvbi5j/b20vaW1hZ2VzL0kv/NTFVckpkTDRFTUwu/anBn"
            )
        )
        fun listaFiltrada(categoria: String): List<Producto> {
            // lista.forEach { it -> categoria.length  }
            return lista.filter { it.categoria.equals(categoria, true) }
        }
    }
}