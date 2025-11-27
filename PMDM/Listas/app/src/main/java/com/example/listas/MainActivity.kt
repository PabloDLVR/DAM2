import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listas.adapter.AdapteProducto
import com.example.listas.databinding.ActivityMainBinding
import com.example.listas.dataset.DataSet
import com.example.listas.model.Producto

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterProducto: AdapteProducto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val lista: ArrayList<Producto> = DataSet.lista

        adapterProducto = AdapteProducto(lista, this)
        if (resources.configuration.orientation == 1) { // posicon del movil vertical
            binding.recyclerProductos.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        } else if (resources.configuration.orientation == 2) { // posicion del movil horizontal
            binding.recyclerProductos.layoutManager =
                GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        }
        binding.recyclerProductos.adapter = adapterProducto

    }
}