package com.cibertec.memopet_proyecto.adapters

import android.view.LayoutInflater
import android.view.View
import android.content.Context
import android.net.Uri
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.memopet_proyecto.R
import com.cibertec.memopet_proyecto.entity.Mascota
import java.text.SimpleDateFormat
import java.util.*

class MascotaAdapter(
    private val context: Context,
    private var lista: List<Mascota>,
    private val onItemClick: (Mascota) -> Unit):
    RecyclerView.Adapter<MascotaAdapter.MascotaViewHolder>() {

    inner class MascotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgMascota: ImageView = itemView.findViewById(R.id.imgMascota)
        val nombre: TextView = itemView.findViewById(R.id.tvNombreMascota)
        val especie: TextView = itemView.findViewById(R.id.tvEspecieMascota)
        val edad: TextView = itemView.findViewById(R.id.tvEdadMascota)
        val imgGenero: ImageView = itemView.findViewById(R.id.icGeneroMascota)


        fun bind(mascota: Mascota) {
            nombre.text = mascota.nombre
            especie.text = mascota.especie
            edad.text = calcularEdad(mascota.fechaNacimiento)

            // Cargar ícono según género
            if (mascota.genero.equals("Macho", ignoreCase = true)) {
                imgGenero.setImageResource(R.mipmap.gmasculino_foreground)
            } else if (mascota.genero.equals("Hembra", ignoreCase = true)) {
                imgGenero.setImageResource(R.mipmap.gfemenino_foreground)
            } else {
                imgGenero.setImageResource(R.mipmap.singe_foreground)
            }

            // Cargar imagen de mascota
            if (!mascota.fotoMasc.isNullOrEmpty()) {
                try {
                    imgMascota.setImageURI(Uri.parse(mascota.fotoMasc))
                } catch (e: Exception) {
                    imgMascota.setImageResource(R.mipmap.sinfoto1)
                }
            } else {
                imgMascota.setImageResource(R.mipmap.sinfoto2)
            }

            itemView.setOnClickListener { onItemClick(mascota) }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MascotaViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mascota, parent, false)
        return MascotaViewHolder(vista)
    }

    override fun onBindViewHolder(holder: MascotaViewHolder, position: Int) {
        val mascota = lista[position]
        holder.bind(mascota)
    }
    override fun getItemCount(): Int = lista.size

    fun actualizarLista(nuevaLista: List<Mascota>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }



    // ---- Función para calcular edad ----
    fun calcularEdad(fechaNac: String): String {
        try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val fecha = sdf.parse(fechaNac)
            val hoy = Calendar.getInstance()

            val nacimiento = Calendar.getInstance()
            nacimiento.time = fecha!!

            var edad = hoy.get(Calendar.YEAR) - nacimiento.get(Calendar.YEAR)
            if (hoy.get(Calendar.DAY_OF_YEAR) < nacimiento.get(Calendar.DAY_OF_YEAR)) {
                edad--
            }
            return "$edad años"
        } catch (e: Exception) {
            e.printStackTrace()
            return "—"
        }
    }
}