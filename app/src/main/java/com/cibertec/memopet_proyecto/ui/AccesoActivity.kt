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
import com.cibertec.memopet_proyecto.data.UsuarioDAO
import com.cibertec.memopet_proyecto.entity.Usuario
import com.google.android.material.textfield.TextInputEditText

class AccesoActivity : AppCompatActivity() {


    private lateinit var etCorreo: TextInputEditText
    private lateinit var etClave: TextInputEditText
    private lateinit var btnInicio: Button
    private lateinit var tvRegistro: TextView
    private lateinit var usuarioDAO: UsuarioDAO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acceso)

        //Inicializar vistas
        etCorreo = findViewById<TextInputEditText>(R.id.tietCorreo)
        etClave = findViewById<TextInputEditText>(R.id.tietClave)
        btnInicio = findViewById<Button>(R.id.btnInicio)
        tvRegistro = findViewById<TextView>(R.id.tvRegistro)
        usuarioDAO = UsuarioDAO(this)


        // Botón Iniciar sesión
        btnInicio.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val clave = etClave.text.toString().trim()

            if (correo.isEmpty() || clave.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val usuario: Usuario? = usuarioDAO.verificar(correo, clave)
            if (usuario != null) {
                Toast.makeText(this, "Bienvenido ${usuario.nombres}", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("idUsuario", usuario.idUsuario)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Correo o clave incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        // Acción del texto "Regístrate aquí"
        tvRegistro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }
}