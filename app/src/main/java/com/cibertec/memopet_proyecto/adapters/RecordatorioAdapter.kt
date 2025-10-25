package com.cibertec.memopet_proyecto.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.memopet_proyecto.R
import com.cibertec.memopet_proyecto.entity.Recordatorio
class RecordatorioAdapter (private val lista: List<Recordatorio>) :
    RecyclerView.Adapter<RecordatorioAdapter.RecordatorioViewHolder>() {

    inner class RecordatorioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgIcono: ImageView = itemView.findViewById(R.id.imgIcono)
        val tvTipo: TextView = itemView.findViewById(R.id.tvTipo)
        val tvFecha: TextView = itemView.findViewById(R.id.tvFecha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordatorioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recordatorio, parent, false)
        return RecordatorioViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: RecordatorioViewHolder, position: Int) {
        val recordatorio = lista[position]
        holder.tvTipo.text = recordatorio.tipo
        holder.tvFecha.text = recordatorio.fecha

        // Cambiar icono según tipo
        val iconRes = when (recordatorio.tipo) {
            "Vacunación" -> R.mipmap.ic_vacuna_foreground
            "Desparasitación" -> R.mipmap.ic_despa_foreground
            "Medicación" -> R.mipmap.ic_medi_foreground
            else -> R.mipmap.ic_vacuna_foreground
        }
        holder.imgIcono.setImageResource(iconRes)
    }
}