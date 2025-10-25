package com.cibertec.memopet_proyecto.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.cibertec.memopet_proyecto.R
import com.cibertec.memopet_proyecto.data.DBHelper
import com.cibertec.memopet_proyecto.data.MascotaDAO
import com.cibertec.memopet_proyecto.entity.Mascota
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class InfoFragment : Fragment(R.layout.fragment_info) {

    private var mascotaId: Int = 0

    companion object {
        private const val ARG_MASCOTA_ID = "mascotaId"

        fun newInstance(mascotaId: Int): InfoFragment {
            val fragment = InfoFragment()
            val args = Bundle()
            args.putInt(ARG_MASCOTA_ID, mascotaId)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var imgMascota: ImageView
    private lateinit var tvNombre: TextView
    private lateinit var tvEspecie: TextView
    private lateinit var tvEdad: TextView
    private lateinit var tvFechaNac: TextView
    private lateinit var tvGenero: TextView
    private lateinit var tvColor: TextView
    private lateinit var tvEsterilizado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { mascotaId = it.getInt(ARG_MASCOTA_ID) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgMascota = view.findViewById(R.id.imginfoMascota)
        tvNombre = view.findViewById(R.id.tvNombreMascota)
        tvEspecie = view.findViewById(R.id.tvEspecie)
        tvEdad = view.findViewById(R.id.tvEdad)
        tvFechaNac = view.findViewById(R.id.tvFechaNac)
        tvGenero = view.findViewById(R.id.tvGenero)
        tvColor = view.findViewById(R.id.tvColor)
        tvEsterilizado = view.findViewById(R.id.tvEsterilizado)

        cargarDatosMascota()
    }

    private fun cargarDatosMascota() {
        val dao = MascotaDAO(requireContext())
        val mascota: Mascota = dao.obtenerPorId(mascotaId) ?: return

        // Cargar imagen
        if (!mascota.fotoMasc.isNullOrEmpty()) {
            imgMascota.setImageURI(android.net.Uri.parse(mascota.fotoMasc))
        } else {
            imgMascota.setImageResource(R.mipmap.mascota2_foreground)
        }

        // Llenar TextViews
        tvNombre.text = "Nombre: ${mascota.nombre}"
        tvEspecie.text = "Especie: ${mascota.especie}"
        tvGenero.text = "Gene            var edad = hoy.get(Calendar.YEAR) - nacimiento.get(Calendar.YEAR)\nro: ${mascota.genero}"
        tvColor.text = "Color: ${mascota.color}"
        tvEsterilizado.text = "Esterilizado: ${mascota.esterilizado}"
        tvFechaNac.text = "Fecha de Nacimiento: ${mascota.fechaNacimiento}"
        tvEdad.text = "Edad: ${calcularEdad(mascota.fechaNacimiento)}"
    }

    private fun calcularEdad(fechaNacimiento: String?): String {
        if (fechaNacimiento.isNullOrEmpty()) return "Desconocida"
        return try {
            val formato = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
            val fecha = formato.parse(fechaNacimiento)
            val hoy = Calendar.getInstance()
            val nacimiento = Calendar.getInstance()
            fecha?.let { nacimiento.time = it }

            var edad = hoy.get(Calendar.YEAR) - nacimiento.get(Calendar.YEAR)
            if (hoy.get(Calendar.DAY_OF_YEAR) < nacimiento.get(Calendar.DAY_OF_YEAR)) {
                edad--
            }
            "$edad años"
        } catch (e: Exception) {
            "Desconocida"
        }
    }
}