package com.cibertec.memopet_proyecto.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cibertec.memopet_proyecto.R
import com.cibertec.memopet_proyecto.data.DBHelper
import com.cibertec.memopet_proyecto.data.UsuarioDAO
import com.cibertec.memopet_proyecto.entity.Usuario
import com.google.android.material.textfield.TextInputEditText

class RegistroActivity : AppCompatActivity() {

    private lateinit var etNombres: TextInputEditText
    private lateinit var etApellidos: TextInputEditText
    private lateinit var etCorreoRegistro: TextInputEditText
    private lateinit var etClaveRegistro: TextInputEditText
    private lateinit var etClaveConfirm: TextInputEditText
    private lateinit var btnRegistro: Button
    private lateinit var tvVolverLogin: TextView
    private lateinit var usuarioDAO: UsuarioDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)


        // Referencias
        etNombres = findViewById<TextInputEditText>(R.id.tietNombres)
        etApellidos = findViewById<TextInputEditText>(R.id.tietApellidos)
        etCorreoRegistro = findViewById<TextInputEditText>(R.id.tietCorreoRegistro)
        etClaveRegistro = findViewById<TextInputEditText>(R.id.tietClaveRegistro)
        etClaveConfirm = findViewById<TextInputEditText>(R.id.tietClaveConfirm)
        btnRegistro = findViewById<Button>(R.id.btnRegistro)
        tvVolverLogin = findViewById<TextView>(R.id.tvVolverLogin)
        usuarioDAO = UsuarioDAO(this)

        // Acción simulada al presionar "Registrarme"
        btnRegistro.setOnClickListener {
            val nombres  = etNombres.text.toString().trim()
            val apellidos  = etApellidos.text.toString().trim()
            val correo = etCorreoRegistro.text.toString().trim()
            val contraseña = etClaveRegistro.text.toString().trim()
            val confirm = etClaveConfirm.text.toString().trim()


            if (nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || contraseña.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (contraseña != confirm) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (usuarioDAO.existeCorreo(correo)) {
                Toast.makeText(this, "El correo ya está registrado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val usuario = Usuario(
                nombres = nombres,
                apellidos = apellidos,
                correo = correo,
                clave = contraseña
            )

            val resultado = usuarioDAO.registrar(usuario)

            if (resultado != -1L) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, AccesoActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show()
            }
        }

        tvVolverLogin.setOnClickListener {
            val intent = Intent(this, AccesoActivity::class.java)
            startActivity(intent)
            finish()
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