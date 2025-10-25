package com.cibertec.memopet_proyecto.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.memopet_proyecto.R
import com.cibertec.memopet_proyecto.adapters.MascotaAdapter
import com.cibertec.memopet_proyecto.data.MascotaDAO
import com.cibertec.memopet_proyecto.entity.Mascota


class HomeActivity : AppCompatActivity() {

    private lateinit var adapter: MascotaAdapter
    private val listamascotas = mutableListOf<Mascota>()
    private lateinit var mascotaDAO: MascotaDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mascotaDAO = MascotaDAO(this)


        val rvMascotas = findViewById<RecyclerView>(R.id.rvMascotas)
        val btnAgregarMascota = findViewById<Button>(R.id.btnAgregarMascota)



        // Configurar adapter
        adapter = MascotaAdapter(listamascotas) { mascota ->
            val intent = Intent(this, DetalleMascotaActivity::class.java)
            intent.putExtra("idMascota", mascota.idMascota)
            startActivity(intent)
        }


        rvMascotas.layoutManager = LinearLayoutManager(this)
        rvMascotas.adapter = adapter

        btnAgregarMascota.setOnClickListener {
            val intent = Intent(this, RegistrarMascotaActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Traer datos de SQLite cada vez que regresa a Home
        cargarMascotas()
    }

    private fun cargarMascotas() {
        val usuarioIdActual = 1 // Cambia según tu lógica de login
        listamascotas.clear()
        listamascotas.addAll(mascotaDAO.obtenerPorUsuario(usuarioIdActual))
        adapter.notifyDataSetChanged()
    }
}
