package com.cibertec.memopet_proyecto.ui

import android.graphics.Bitmap
import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.net.Uri
import android.content.Intent
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import com.cibertec.memopet_proyecto.R
import com.cibertec.memopet_proyecto.entity.Mascota
import java.io.IOException
import java.util.*


class RegistrarMascotaActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var spEspecie: Spinner
    private lateinit var etFecha: EditText
    private lateinit var rgVacunas: RadioGroup
    private lateinit var rbSi: RadioButton
    private lateinit var rbNo: RadioButton
    private lateinit var btnGuardar: Button
    private lateinit var imgMascota: ImageView
    private lateinit var btnSeleccionarFoto: Button

    private var imagenUri: Uri? = null
    private var bitmapSeleccionado: Bitmap? = null

    // Lanza galería
    private val seleccionarImagenLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                imagenUri = result.data!!.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imagenUri)
                    imgMascota.setImageBitmap(bitmap)
                    bitmapSeleccionado = bitmap
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

    // Lanza cámara
    private val tomarFotoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val bitmap = result.data!!.extras?.get("data") as Bitmap
                imgMascota.setImageBitmap(bitmap)
                bitmapSeleccionado = bitmap
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_mascota)

        etNombre = findViewById(R.id.etNombreMascota)
        spEspecie = findViewById(R.id.spEspecie)
        etFecha = findViewById(R.id.etFechaNacimiento)
        rgVacunas = findViewById(R.id.rgVacunas)
        rbSi = findViewById(R.id.rbSiVacunas)
        rbNo = findViewById(R.id.rbNoVacunas)
        btnGuardar = findViewById(R.id.btnGuardarMascota)
        imgMascota = findViewById(R.id.imgMascota)
        btnSeleccionarFoto = findViewById(R.id.btnSeleccionarFoto)

        val especies = listOf("Perro", "Gato", "Loro", "Conejo")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, especies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spEspecie.adapter = adapter

        // Seleccionar fecha
        etFecha.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    etFecha.setText("$day/${month + 1}/$year")
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Elegir foto
        btnSeleccionarFoto.setOnClickListener { mostrarDialogoFoto() }

        // Guardar
        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val especie = spEspecie.selectedItem.toString()
            val fecha = etFecha.text.toString().trim()
            val tieneVacunas = rbSi.isChecked

            if (nombre.isEmpty() || fecha.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val mascota = Mascota(
                id = 0,
                nombre = nombre,
                especie = especie,
                fechaNacimiento = fecha,
                fotoResId = R.mipmap.mascota// por ahora
            )

            Toast.makeText(
                this,
                "Mascota registrada: ${mascota.nombre} (${mascota.especie})",
                Toast.LENGTH_LONG
            ).show()

            // Redirigir según la opción de vacunas
            if (tieneVacunas) {
                // Si marcó Sí → RegistrarVacunasActivity
                val intent = Intent(this, RegistrarVacunasActivity::class.java)
                intent.putExtra("ID_MASCOTA", mascota.id) // Pasar ID si lo guardas en SQLite
                startActivity(intent)
            } else {
                // Si marcó No → CalendarioVacunasActivity
                val intent = Intent(this, CalendarioVacunasActivity::class.java)
                intent.putExtra("ID_MASCOTA", mascota.id)
                startActivity(intent)
            }

            finish()
        }
    }

    private fun mostrarDialogoFoto() {
        val opciones = arrayOf("Tomar foto", "Elegir de galería")
        AlertDialog.Builder(this)
            .setTitle("Seleccionar imagen")
            .setItems(opciones) { _, i ->
                when (i) {
                    0 -> abrirCamara()
                    1 -> abrirGaleria()
                }
            }
            .show()
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        seleccionarImagenLauncher.launch(intent)
    }

    private fun abrirCamara() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        tomarFotoLauncher.launch(intent)
    }

}