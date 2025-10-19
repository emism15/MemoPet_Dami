package com.cibertec.memopet_proyecto.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.memopet_proyecto.R
import com.cibertec.memopet_proyecto.entity.Vacuna

class VacunaAdapter(

    private val context: Context,
    private val lista: List<Vacuna>
) : RecyclerView.Adapter<VacunaAdapter.VacunaViewHolder>() {

    inner class VacunaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombreVacuna)
        val tvFecha: TextView = view.findViewById(R.id.tvFechaVacuna)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacunaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_vacuna, parent, false)
        return VacunaViewHolder(view)
    }

    override fun onBindViewHolder(holder: VacunaViewHolder, position: Int) {
        val vacuna = lista[position]
        holder.tvNombre.text = vacuna.nombre
        holder.tvFecha.text = vacuna.fecha

    }

    override fun getItemCount(): Int = lista.size
}
