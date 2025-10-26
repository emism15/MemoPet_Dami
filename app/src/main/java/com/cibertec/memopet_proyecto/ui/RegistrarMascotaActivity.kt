package com.cibertec.memopet_proyecto.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cibertec.memopet_proyecto.R
import com.cibertec.memopet_proyecto.data.MascotaDAO
import com.cibertec.memopet_proyecto.entity.Mascota
import java.io.IOException
import java.util.*

class RegistrarMascotaActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var spEspecie: Spinner
    private lateinit var etFecha: EditText
    private lateinit var rbSi: RadioButton
    private lateinit var rbNo: RadioButton
    private lateinit var rbMacho: RadioButton
    private lateinit var rbHembra: RadioButton
    private lateinit var btnGuardar: Button
    private lateinit var imgMascota: ImageView
    private lateinit var btnSeleccionarFoto: Button
    private lateinit var tieColor: EditText

    private var imagenUri: Uri? = null
    private var bitmapSeleccionado: Bitmap? = null

    // DAO
    private lateinit var mascotaDAO: MascotaDAO

    // Galería
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

    // Cámara
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

        // Instanciar DAO
        mascotaDAO = MascotaDAO(this)

        // Enlazar vistas
        etNombre = findViewById(R.id.etNomMascota)
        spEspecie = findViewById(R.id.etEspecie)
        etFecha = findViewById(R.id.etFecNacimiento)
        rbSi = findViewById(R.id.rbSiEsterilizado)
        rbNo = findViewById(R.id.rbNoEsterilizado)
        rbMacho = findViewById(R.id.rbMacho)
        rbHembra = findViewById(R.id.rbHembra)
        tieColor = findViewById(R.id.etColor)
        btnGuardar = findViewById(R.id.btnGuardarMascota)
        imgMascota = findViewById(R.id.imgNewMascota)
        btnSeleccionarFoto = findViewById(R.id.btnSeleccionarFoto)

        // Spinner de especies
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

        // Seleccionar foto
        btnSeleccionarFoto.setOnClickListener { mostrarDialogoFoto() }

        // Guardar mascota
        btnGuardar.setOnClickListener { guardarMascota() }
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

    private fun guardarMascota() {
        val nombre = etNombre.text.toString().trim()
        val especie = spEspecie.selectedItem.toString()
        val fecha = etFecha.text.toString().trim()
        val genero = when {
            rbMacho.isChecked -> "Macho"
            rbHembra.isChecked -> "Hembra"
            else -> "No especificado"
        }
        val color = tieColor.text.toString().trim()
        val esterilizado = if (rbSi.isChecked) "Sí" else "No"

        if (nombre.isEmpty() || fecha.isEmpty() || color.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val sharedPref = getSharedPreferences("usuario_prefs", Context.MODE_PRIVATE)
        val idUsuario = sharedPref.getInt("idUsuario", 0)

        val mascota = Mascota(
            idMascota = 0,
            nombre = nombre,
            especie = especie,
            genero = genero,
            color = color,
            esterilizado = esterilizado == "Sí", // Booleano
            fechaNacimiento = fecha,
            fotoMasc = imagenUri?.toString() ?: "",
            idUsuario = idUsuario
        )

        val idInsertado = mascotaDAO.insertar(mascota)

        if (idInsertado > 0) {
            Toast.makeText(this, "Mascota registrada correctamente 🐾", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Error al registrar la mascota 😢", Toast.LENGTH_SHORT).show()
        }
    }
}
