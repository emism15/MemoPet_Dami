package com.cibertec.memopet_proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)


        // Referencia al botón de registro
        val btnRegistro = findViewById<Button>(R.id.btnRegistro)

        // Acción simulada al presionar "Registrarme"
        btnRegistro.setOnClickListener {
            Toast.makeText(this, "Simulación: Registro exitoso", Toast.LENGTH_SHORT).show()
        }

        // Esto hace que el teclado no tape los campos
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                maxOf(systemBars.bottom, imeInsets.bottom)
            )
            insets
        }
    }



}