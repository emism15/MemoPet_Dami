package com.cibertec.memopet_proyecto.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.cibertec.memopet_proyecto.R
import com.cibertec.memopet_proyecto.adapters.VacunaAdapter
import com.cibertec.memopet_proyecto.data.DBHelper
import com.cibertec.memopet_proyecto.entity.Vacuna

class PerfilMascotaActivity : AppCompatActivity() {

    private lateinit var imgMascota: ImageView
    private lateinit var tvNombre: TextView
    private lateinit var tvEspecie: TextView
    private lateinit var rvVacunas: RecyclerView
    private lateinit var rvDesparasitaciones: RecyclerView
    private lateinit var btnAgregarVacuna: Button
    private lateinit var dbHelper: DBHelper

    private var idMascota: Long = 0
    private lateinit var listaVacunas: MutableList<Vacuna>
    private lateinit var listaDesparasitaciones: MutableList<Vacuna>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_mascota)

        imgMascota = findViewById(R.id.imgPerfilMascota)
        tvNombre = findViewById(R.id.tvNombrePerfil)
        tvEspecie = findViewById(R.id.tvEspeciePerfil)
        rvVacunas = findViewById(R.id.rvVacunas)
        rvDesparasitaciones = findViewById(R.id.rvDesparasitaciones)
        btnAgregarVacuna = findViewById(R.id.btnAgregarVacuna)
        dbHelper = DBHelper(this)

        // Recibir datos de HomeActivity
        idMascota = intent.getLongExtra("ID_MASCOTA", 0)
        val nombreMascota = intent.getStringExtra("NOMBRE_MASCOTA") ?: "Sin nombre"
        val especieMascota = intent.getStringExtra("ESPECIE_MASCOTA") ?: "Sin especie"
        val fotoMascota = intent.getIntExtra("FOTO_MASCOTA", R.mipmap.mascota)

        tvNombre.text = nombreMascota
        tvEspecie.text = especieMascota
        imgMascota.setImageResource(fotoMascota)

        // Configurar RecyclerViews
        rvVacunas.layoutManager = LinearLayoutManager(this)
        rvDesparasitaciones.layoutManager = LinearLayoutManager(this)

        // Cargar datos de vacunas y desparasitaciones
        cargarVacunas()
        cargarDesparasitaciones()

        // Botón para agregar vacuna
        btnAgregarVacuna.setOnClickListener {
            val intent = Intent(this, RegistrarVacunasActivity::class.java)
            intent.putExtra("ID_MASCOTA", idMascota)
            startActivity(intent)
        }
    }

    private fun cargarVacunas() {
        listaVacunas = mutableListOf()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT nombre_vacuna, fecha_aplicacion FROM Vacuna WHERE mascota_id = ? AND nombre_vacuna LIKE 'Vacuna%'",
            arrayOf(idMascota.toString())
        )
        if (cursor.moveToFirst()) {
            do {
                val vacuna = Vacuna(
                    id = 0,
                    mascotaId = idMascota,
                    nombre = cursor.getString(0),
                    fecha = cursor.getString(1)
                )
                listaVacunas.add(vacuna)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        rvVacunas.adapter = VacunaAdapter(this, listaVacunas)
    }

    private fun cargarDesparasitaciones() {
        listaDesparasitaciones = mutableListOf()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT nombre_vacuna, fecha_aplicacion FROM Vacuna WHERE mascota_id = ? AND nombre_vacuna LIKE 'Desparasitacion%'",
            arrayOf(idMascota.toString())
        )
        if (cursor.moveToFirst()) {
            do {
                val des = Vacuna(
                    id = 0,
                    mascotaId = idMascota,
                    nombre = cursor.getString(0),
                    fecha = cursor.getString(1)
                )
                listaDesparasitaciones.add(des)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        rvDesparasitaciones.adapter = VacunaAdapter(this, listaDesparasitaciones)
    }
}
