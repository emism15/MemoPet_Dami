package com.cibertec.memopet_proyecto.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.memopet_proyecto.R
import com.cibertec.memopet_proyecto.entity.Mascota

class MascotaAdapter(

    private val listaMascotas: List<Mascota>,
    private val onClick: (Mascota) -> Unit
) : RecyclerView.Adapter<MascotaAdapter.MascotaViewHolder>() {


    inner class MascotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foto: ImageView = itemView.findViewById(R.id.imgMascota)
        val nombre: TextView = itemView.findViewById(R.id.tvNombre)
        val especie: TextView = itemView.findViewById(R.id.tvEspecie)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MascotaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mascota, parent, false)
        return MascotaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MascotaViewHolder, position: Int) {
        val mascota = listaMascotas[position]
        holder.foto.setImageResource(mascota.fotoResId)
        holder.nombre.text = mascota.nombre
        holder.especie.text = mascota.especie
        holder.itemView.setOnClickListener {
            onClick(mascota)  // Aquí llamamos al listener
        }
    }

    override fun getItemCount(): Int = listaMascotas.size
}