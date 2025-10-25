package com.cibertec.memopet_proyecto.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.cibertec.memopet_proyecto.R
import com.cibertec.memopet_proyecto.data.DBHelper
import com.cibertec.memopet_proyecto.data.RecordatorioDAO
import com.cibertec.memopet_proyecto.entity.Recordatorio
import java.util.*


class VacunacionActivity : AppCompatActivity() {

    private lateinit var etNota: EditText
    private lateinit var etFecha: EditText
    private lateinit var etHora: EditText
    private lateinit var btnGuardar: Button
    private lateinit var recordatorioDao: RecordatorioDAO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vacunacion)

        etNota = findViewById(R.id.etNota)
        etFecha = findViewById(R.id.etFecha)
        etHora = findViewById(R.id.etHora)
        btnGuardar = findViewById(R.id.btnGuardar)

        // Inicializar DAO
//        val dbHelper = DBHelper(this) por ahora no
        recordatorioDao = RecordatorioDAO(this)

        // Seleccionar fecha
        etFecha.setOnClickListener {
            val calendario = Calendar.getInstance()
            val year = calendario.get(Calendar.YEAR)
            val month = calendario.get(Calendar.MONTH)
            val day = calendario.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, { _, y, m, d ->
                val fechaSeleccionada = String.format("%02d/%02d/%d", d, m + 1, y)
                etFecha.setText(fechaSeleccionada)
            }, year, month, day)

            datePicker.show()
        }

        // Seleccionar hora
        etHora.setOnClickListener {
            val calendario = Calendar.getInstance()
            val hour = calendario.get(Calendar.HOUR_OF_DAY)
            val minute = calendario.get(Calendar.MINUTE)

            val timePicker = TimePickerDialog(this, { _, h, m ->
                val horaSeleccionada = String.format("%02d:%02d", h, m)
                etHora.setText(horaSeleccionada)
            }, hour, minute, true)

            timePicker.show()
        }

        // Guardar recordatorio
        btnGuardar.setOnClickListener {
            val nota = etNota.text.toString()
            val fecha = etFecha.text.toString()
            val hora = etHora.text.toString()

            if (nota.isEmpty() || fecha.isEmpty() || hora.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Suponiendo que pasaste el idMascota desde RecordatorioFragment
            val mascotaId = intent.getIntExtra("mascotaId", -1)
            if (mascotaId == -1) {
                Toast.makeText(this, "Error: no se encontró la mascota", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nuevoRecordatorio = Recordatorio(
                id = 0,
                mascotaId = mascotaId,
                tipo = "Vacunación",
                fecha = fecha,
                hora = hora,
                nota = nota
            )

            recordatorioDao.agregar(nuevoRecordatorio)
            Toast.makeText(this, "Recordatorio guardado correctamente", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}