package com.cibertec.memopet_proyecto.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cibertec.memopet_proyecto.ui.HomeActivity
import com.cibertec.memopet_proyecto.R
import com.cibertec.memopet_proyecto.data.DBHelper
import com.google.android.material.textfield.TextInputEditText

class AccesoActivity : AppCompatActivity() {


    private lateinit var etCorreo: TextInputEditText
    private lateinit var etClave: TextInputEditText
    private lateinit var btnInicio: Button
    private lateinit var tvRegistro: TextView
    private lateinit var dbHelper: DBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acceso)

        etCorreo = findViewById<TextInputEditText>(R.id.tietCorreo)
        etClave = findViewById<TextInputEditText>(R.id.tietClave)
        btnInicio = findViewById<Button>(R.id.btnInicio)
        tvRegistro = findViewById<TextView>(R.id.tvRegistro)
        dbHelper = DBHelper(this)

        // Botón Iniciar sesión
        btnInicio.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val clave = etClave.text.toString().trim()

            if (correo.isEmpty() || clave.isEmpty()) {
                Toast.makeText(this, "Por favor, completar todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val userId = dbHelper.validarUsuario(correo, clave)
                if (userId != -1) {
                    // Guardar sesión con SharedPreferences
                    val sharedPref = getSharedPreferences("MemoPetPrefs", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putBoolean("isLoggedIn", true)
                        putInt("userId", userId)
                        putString("correo", correo)
                        apply()
                    }

                    Toast.makeText(this, "Bienvenido a MemoPet", Toast.LENGTH_SHORT).show()

                    // Ir a la pantalla principal
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