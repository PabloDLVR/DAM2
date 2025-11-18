import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.examen_android.R

class CombustibleAdapter(
    context: Context,
    private val combustibles: List<Combustible>
) : ArrayAdapter<Combustible>(context, 0, combustibles) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_combustible, parent, false)

        val combustible = combustibles[position]

        val icono = view.findViewById<ImageView>(R.id.ivIcono)
        val nombre = view.findViewById<TextView>(R.id.tvNombre)

        icono.setImageResource(combustible.icono)
        nombre.text = combustible.nombre

        return view
    }
}

data class Combustible(
    val nombre: String,
    val icono: Int
)