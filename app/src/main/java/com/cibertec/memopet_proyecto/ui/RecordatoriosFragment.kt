package com.cibertec.memopet_proyecto.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.cibertec.memopet_proyecto.R
import com.cibertec.memopet_proyecto.data.RecordatorioDAO
import com.cibertec.memopet_proyecto.entity.Recordatorio

class RecordatoriosFragment: Fragment(R.layout.fragment_recordatorios) {

    private var mascotaId: Int = 0

    companion object {
        private const val ARG_MASCOTA_ID = "mascotaId"
        fun newInstance(mascotaId: Int): RecordatoriosFragment {
            val fragment = RecordatoriosFragment()
            val args = Bundle()
            args.putInt(ARG_MASCOTA_ID, mascotaId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recordatorios, container, false)
        arguments?.let { mascotaId = it.getInt(ARG_MASCOTA_ID) }

        val dao = RecordatorioDAO(requireContext())

        view.findViewById<LinearLayout>(R.id.cardVacunacion).setOnClickListener {
            dao.agregar(Recordatorio(mascotaId = mascotaId, tipo = "Vacunación"))
            Toast.makeText(context, "Vacunación guardada", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<LinearLayout>(R.id.cardDesparasitacion).setOnClickListener {
            dao.agregar(Recordatorio(mascotaId = mascotaId, tipo = "Desparasitación"))
            Toast.makeText(context, "Desparasitación guardada", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<LinearLayout>(R.id.cardMedicacion).setOnClickListener {
            dao.agregar(Recordatorio(mascotaId = mascotaId, tipo = "Medicación"))
            Toast.makeText(context, "Medicación guardada", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}