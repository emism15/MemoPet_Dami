package com.cibertec.memopet_proyecto

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class BienvenidaActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)

        val btnStart = findViewById<Button>(R.id.btnEmpezar)

        btnStart.setOnClickListener {
            val intent = Intent(this, AccesoActivity::class.java)
            startActivity(intent)
            finish() // para que no regrese a welcome
        }
    }
}