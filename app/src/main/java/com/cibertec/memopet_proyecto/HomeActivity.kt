package com.cibertec.memopet_proyecto

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
class HomeActivity : AppCompatActivity(){
    private lateinit var rvMascotas: RecyclerView
    private lateinit var fabAgregar: FloatingActionButton
    private val listaMascotas = mutableListOf<Mascota>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        rvMascotas = findViewById(R.id.rvMascotas)
        fabAgregar = findViewById(R.id.AgregarMascota)

        // Datos simulados
        listaMascotas.add(Mascota("Milo", "Perro", "Mostaza", "03/05/2025"))
        listaMascotas.add(Mascota("Michi", "Gato", "Blanco", "13/08/2023"))

        /*// Configurar RecyclerView
        rvMascotas.layoutManager = LinearLayoutManager(this)
        rvMascotas.adapter = MascotaAdapter(listaMascotas)*/

        // Acción botón agregar
        fabAgregar.setOnClickListener {
            Toast.makeText(this, "Simulación: abrir formulario para agregar mascota", Toast.LENGTH_SHORT).show()
        }
    }


}
