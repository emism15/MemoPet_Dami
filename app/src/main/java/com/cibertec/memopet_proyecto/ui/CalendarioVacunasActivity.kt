package com.cibertec.memopet_proyecto.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.cibertec.memopet_proyecto.R
import android.widget.Toast
import com.cibertec.memopet_proyecto.data.DBHelper
import java.text.SimpleDateFormat
import java.util.*

class CalendarioVacunasActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private var idMascota: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendario_vacunas)

        dbHelper = DBHelper(this)
        idMascota = intent.getLongExtra("ID_MASCOTA", 0)

        generarEsquemaAutomatico()
    }

    private fun generarEsquemaAutomatico() {
        val db = dbHelper.writableDatabase

        // Leer fechaNacimiento y especie de la mascota
        val cursor = db.rawQuery("SELECT fecha_nacimiento, especie FROM Mascota WHERE id = ?",
            arrayOf(idMascota.toString()))
        if(cursor.moveToFirst()) {
            val fechaNacimiento = cursor.getString(0)
            val especie = cursor.getString(1)

            val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val cal = Calendar.getInstance()
            cal.time = formato.parse(fechaNacimiento)!!

            // Esquema ejemplo según especie (solo perros en este ejemplo)
            val vacunas = if (especie == "Perro") listOf("Desparasitacion 1", "Vacuna 1", "Vacuna 2") else listOf("Vacuna 1")
            val dias = if (especie == "Perro") listOf(30, 45, 60) else listOf(30)

            for(i in vacunas.indices){
                val c = cal.clone() as Calendar
                c.add(Calendar.DAY_OF_YEAR, dias[i])

                val values = android.content.ContentValues().apply {
                    put("mascota_id", idMascota)
                    put("nombre_vacuna", vacunas[i])
                    put("fecha_aplicacion", formato.format(c.time))
                }
                db.insert("Vacuna", null, values)
            }

            Toast.makeText(this, "Esquema de vacunas generado automáticamente", Toast.LENGTH_LONG).show()
        }
        cursor.close()
        db.close()

        finish() // cerrar la pantalla y volver al Home
    }
}