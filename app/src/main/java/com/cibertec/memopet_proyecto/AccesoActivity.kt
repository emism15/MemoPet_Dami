package com.cibertec.memopet_proyecto

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AccesoActivity : AppCompatActivity() {

    // 1. Declaraciones de vistas usando lateinit
    private lateinit var tvRegistro: TextView
    private lateinit var tietCorreo: TextInputEditText
    private lateinit var tietClave: TextInputEditText
    private lateinit var tilCorreo: TextInputLayout
    private lateinit var tilClave: TextInputLayout
    private lateinit var btnAcceso: Button

    // Lista simulada de usuarios
    private val listaUsuarios = listOf(
        Usuario(1, "Emily", "Silva", "emily@gmail.com", "1234"),
        Usuario(2, "Ariana", "More", "arian@gmail.com", "2468")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Asegúrate de que el layout sea el correcto (R.layout.activity_acceso)
        setContentView(R.layout.activity_acceso)

        // 2. Inicialización de vistas (conexión con el XML)
        tvRegistro = findViewById(R.id.tvRegistro)
        tietCorreo = findViewById(R.id.tietCorreo)
        tietClave = findViewById(R.id.tietClave)
        tilCorreo = findViewById(R.id.tilCorreo)
        tilClave = findViewById(R.id.tilClave)
        btnAcceso = findViewById(R.id.btnInicio)


        btnAcceso.setOnClickListener {
            validarYAutenticar()
        }

        // El texto de Registro navega a la siguiente Activity
        tvRegistro.setOnClickListener {
            cambioActivity(RegistroActivity::class.java)
        }
    }

    private fun validarYAutenticar() {
        val correoInput = tietCorreo.text.toString().trim()
        val claveInput = tietClave.text.toString().trim()
        var tieneError: Boolean = false

        // Validación de Vacío: Correo
        if (correoInput.isEmpty()) {
            tilCorreo.error = "Ingresa tu correo electrónico"
            tieneError = true
        } else {
            tilCorreo.error = null // Limpia el error
        }

        // Validación de Vacío: Contraseña
        if (claveInput.isEmpty()) {
            tilClave.error = "Ingresa tu contraseña"
            tieneError = true
        } else {
            tilClave.error = null // Limpia el error
        }

        // Detiene la ejecución si hay errores de campo
        if (tieneError) {
            Toast.makeText(this, "Por favor, completa los campos obligatorios.", Toast.LENGTH_SHORT).show()
            return
        }

        // 4. Simulación de Autenticación
        // Usamos la función 'find' de Kotlin para buscar un usuario que cumpla la condición
        val usuarioEncontrado = listaUsuarios.find {
            // Buscamos sin distinción de mayúsculas/minúsculas en el correo
            it.correo.equals(correoInput, ignoreCase = true) && it.clave == claveInput
        }

        if (usuarioEncontrado != null) {
            // Éxito: Navega al MainActivity y termina esta Activity
            Toast.makeText(this, "¡Bienvenido, ${usuarioEncontrado.nombres}!", Toast.LENGTH_LONG).show()
            cambioActivity(MainActivity::class.java, finishCurrent = true)
        } else {
            // Fallo: Muestra mensaje de error
            Toast.makeText(this, "Error: Correo o contraseña incorrectos.", Toast.LENGTH_LONG).show()
            tietClave.setText("") // Buena práctica: limpiar la clave después de un intento fallido
        }
    }


    private fun cambioActivity(activityDestino: Class<out Activity>, finishCurrent: Boolean = false) {
        val intent = Intent(this, activityDestino)
        startActivity(intent)
        if (finishCurrent) {
            finish()
        }
    }
}


