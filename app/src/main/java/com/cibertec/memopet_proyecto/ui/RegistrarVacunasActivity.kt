package com.cibertec.memopet_proyecto.ui

import android.app.DatePickerDialog
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.cibertec.memopet_proyecto.R
import com.cibertec.memopet_proyecto.data.DBHelper
import java.util.*


class RegistrarVacunasActivity : AppCompatActivity() {

    private lateinit var etNombreVacuna: EditText
    private lateinit var etFechaAplicacion: EditText
    private lateinit var etObservaciones: EditText
    private lateinit var btnSeleccionarFecha: Button
    private lateinit var btnGuardarVacuna: Button
    private lateinit var btnFinalizar: Button
    private lateinit var dbHelper: DBHelper

    private var idMascota: Long = 0 // Recibido desde RegistrarMascotaActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_vacunas)

        etNombreVacuna = findViewById(R.id.etNombreVacuna)
        etFechaAplicacion = findViewById(R.id.etFechaAplicacion)
        btnSeleccionarFecha = findViewById(R.id.btnSeleccionarFecha)
        btnGuardarVacuna = findViewById(R.id.btnGuardarVacuna)
        btnFinalizar = findViewById(R.id.btnFinalizar)

        dbHelper = DBHelper(this)

        // Recibir ID de la mascota
        idMascota = intent.getLongExtra("ID_MASCOTA", 0)

        // Seleccionar fecha
        btnSeleccionarFecha.setOnClickListener {
            mostrarDatePicker()
        }

        // Guardar vacuna
        btnGuardarVacuna.setOnClickListener {
            guardarVacuna()
        }

        // Finalizar registro
        btnFinalizar.setOnClickListener {
            Toast.makeText(this, "Registro de vacunas finalizado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun mostrarDatePicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, { _, y, m, d ->
            etFechaAplicacion.setText("$d/${m + 1}/$y")
        }, year, month, day)
        dpd.show()
    }

    private fun guardarVacuna() {
        val nombreVacuna = etNombreVacuna.text.toString().trim()
        val fecha = etFechaAplicacion.text.toString().trim()
        val obs = etObservaciones.text.toString().trim()

        if (nombreVacuna.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("mascota_id", idMascota)
            put("nombre_vacuna", nombreVacuna)
            put("fecha_aplicacion", fecha)
        }

        val result = db.insert("Vacuna", null, values)
        db.close()

        if (result != -1L) {
            Toast.makeText(this, "Vacuna guardada correctamente", Toast.LENGTH_SHORT).show()
            etNombreVacuna.text.clear()
            etFechaAplicacion.text.clear()
            etObservaciones.text.clear()
        } else {
            Toast.makeText(this, "Error al guardar vacuna", Toast.LENGTH_SHORT).show()
        }
    }
}