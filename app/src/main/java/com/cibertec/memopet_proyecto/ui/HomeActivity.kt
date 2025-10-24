package com.cibertec.memopet_proyecto.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.memopet_proyecto.R
import com.cibertec.memopet_proyecto.data.MascotaDAO
import com.cibertec.memopet_proyecto.entity.Mascota
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeActivity : AppCompatActivity() {

    private lateinit var adapter: MascotaAdapter
    private val mascotas = mutableListOf<Mascota>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val rvMascotas = findViewById<RecyclerView>(R.id.rvMascotas)
        val btnAgregarMascota = findViewById<Button>(R.id.btnAgregarMascota)



        adapter = MascotaAdapter(mascotas) { mascota ->
            val intent = Intent(this, PerfilMascotaActivity::class.java)
            intent.putExtra("nombre", mascota.nombre)
            intent.putExtra("especie", mascota.especie)
            intent.putExtra("fechaNacimiento", mascota.fechaNacimiento)
            intent.putExtra("fotoResId", mascota.fotoMasc)
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
        adapter.notifyDataSetChanged()
    }
}
