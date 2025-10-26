package com.cibertec.memopet_proyecto.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.cibertec.memopet_proyecto.entity.Mascota

class MascotaDAO(context: Context) {
    private val dbHelper = DBHelper(context)

    fun insertar(mascota: Mascota): Long {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("nombre", mascota.nombre)
            put("especie", mascota.especie)
            put("genero", mascota.genero)
            put("color", mascota.color)
            put("esterilizado", if (mascota.esterilizado) 1 else 0) // guardar como 0/1
            put("fechaNacimiento", mascota.fechaNacimiento)
            put("fotoMasc", mascota.fotoMasc)
            put("idUsuario", mascota.idUsuario)
        }
        val resultado = db.insert("mascota", null, valores)
        db.close()
        return resultado
    }

    fun obtenerPorUsuario(idUsuario: Int): List<Mascota> {
        val lista = mutableListOf<Mascota>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM mascota WHERE idUsuario = ?", arrayOf(idUsuario.toString()))

        if (cursor.moveToFirst()) {
            do {
                val mascota = Mascota(
                    idMascota = cursor.getInt(cursor.getColumnIndexOrThrow("idMascota")),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                    especie = cursor.getString(cursor.getColumnIndexOrThrow("especie")),
                    genero = cursor.getString(cursor.getColumnIndexOrThrow("genero")),
                    color = cursor.getString(cursor.getColumnIndexOrThrow("color")),
                    esterilizado = cursor.getInt(cursor.getColumnIndexOrThrow("esterilizado")) > 0,
                    fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow("fechaNacimiento")),
                    fotoMasc = cursor.getString(cursor.getColumnIndexOrThrow("fotoMasc")),
                    idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow("idUsuario"))
                )
                lista.add(mascota)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }


    fun obtenerPorId(id: Int): Mascota? {
        val db = dbHelper.readableDatabase
        var mascota: Mascota? = null
        val cursor = db.rawQuery("SELECT * FROM mascota WHERE idMascota = ?", arrayOf(id.toString()))
        if (cursor.moveToFirst()) {
            mascota = Mascota(
                idMascota = cursor.getInt(cursor.getColumnIndexOrThrow("idMascota")),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                especie = cursor.getString(cursor.getColumnIndexOrThrow("especie")),
                genero = cursor.getString(cursor.getColumnIndexOrThrow("genero")),
                color = cursor.getString(cursor.getColumnIndexOrThrow("color")),
                esterilizado = cursor.getInt(cursor.getColumnIndexOrThrow("esterilizado")) == 1,
                fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow("fechaNacimiento")),
                fotoMasc = cursor.getString(cursor.getColumnIndexOrThrow("fotoMasc")),
                idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow("idUsuario"))
            )
        }
        cursor.close()
        db.close()
        return mascota
    }





}
