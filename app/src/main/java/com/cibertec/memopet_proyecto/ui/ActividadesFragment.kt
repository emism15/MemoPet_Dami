package com.cibertec.memopet_proyecto.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.memopet_proyecto.R
import com.cibertec.memopet_proyecto.adapters.RecordatorioAdapter
import com.cibertec.memopet_proyecto.data.RecordatorioDAO
import com.cibertec.memopet_proyecto.entity.Recordatorio

class ActividadesFragment : Fragment(R.layout.fragment_actividades) {

    private var mascotaId: Int = 0
    private lateinit var rvRecordatorios: RecyclerView
    private lateinit var adapter: RecordatorioAdapter
    private var listaRecordatorios = mutableListOf<Recordatorio>()

    companion object {
        private const val ARG_MASCOTA_ID = "mascota_id"

        fun newInstance(mascotaId: Int): ActividadesFragment {
            return ActividadesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_MASCOTA_ID, mascotaId)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mascotaId = it.getInt(ARG_MASCOTA_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_actividades, container, false)

        rvRecordatorios = view.findViewById(R.id.rvRecordatorios)
        rvRecordatorios.layoutManager = LinearLayoutManager(context)

        // Cargar recordatorios desde SQLite
        val dao = RecordatorioDAO(requireContext())
        listaRecordatorios = dao.obtenerPorMascota(mascotaId).toMutableList()
        adapter = RecordatorioAdapter(listaRecordatorios)
        rvRecordatorios.adapter = adapter

        // Configurar calendario si lo quieres
        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        calendarView?.setOnDateChangeListener { _, year, month, dayOfMonth ->
        }

        return view
    }
}
