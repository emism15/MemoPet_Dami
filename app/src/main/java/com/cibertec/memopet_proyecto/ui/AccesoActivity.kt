package com.cibertec.memopet_proyecto.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cibertec.memopet_proyecto.ui.HomeActivity
import com.cibertec.memopet_proyecto.R
import com.cibertec.memopet_proyecto.ui.RegistroActivity
import com.google.android.material.textfield.TextInputEditText

class AccesoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acceso)

        val etCorreo = findViewById<TextInputEditText>(R.id.tietCorreo)
        val etClave = findViewById<TextInputEditText>(R.id.tietClave)
        val btnInicio = findViewById<Button>(R.id.btnInicio)
        val tvRegistro = findViewById<TextView>(R.id.tvRegistro)

        btnInicio.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val clave = etClave.text.toString().trim()

            if (correo.isEmpty() || clave.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa tu correo y contraseña", Toast.LENGTH_SHORT).show()
            } else {

                if (correo == "prueba@gmail.com" && clave == "1234") {
                    // Login exitoso → abrir HomeActivity
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Acción del texto "Regístrate aquí"
        tvRegistro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }
}