package com.cibertec.memopet_proyecto.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.cibertec.memopet_proyecto.R

class BienvenidaActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)

        val btnEmpezar = findViewById<Button>(R.id.btnEmpezar)
        btnEmpezar.setOnClickListener {
            val intent = Intent(this, AccesoActivity::class.java)
            startActivity(intent)
            finish() // para que no regrese a welcome
        }
    }
}