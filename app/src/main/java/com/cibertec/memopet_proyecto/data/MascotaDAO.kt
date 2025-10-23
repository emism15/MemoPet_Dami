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
            put("esterilizado", mascota.esterilizado)
            put("fecha_nacimiento", mascota.fechaNacimiento)
            put("foto", mascota.fotoMasc)
            put("id_usuario", mascota.idUsuario)
        }
        val resultado = db.insert("mascota", null, valores)
        db.close()
        return resultado
    }

    fun obtenerPorUsuario(idUsuario: Int): List<Mascota> {
        val db = dbHelper.readableDatabase
        val lista = mutableListOf<Mascota>()
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM mascota WHERE idUsuario = ?",
            arrayOf(idUsuario.toString())
        )

        while (cursor.moveToNext()) {
            lista.add(
                Mascota(
                    idMascota = cursor.getInt(cursor.getColumnIndexOrThrow("idMascota")),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                    especie = cursor.getString(cursor.getColumnIndexOrThrow("especie")),
                    genero = cursor.getString(cursor.getColumnIndexOrThrow("genero")),
                    color = cursor.getString(cursor.getColumnIndexOrThrow("color")),
                    esterilizado = cursor.getString(cursor.getColumnIndexOrThrow("esterilizado")),
                    fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow("fechaNacimiento")),
                    fotoMasc = cursor.getString(cursor.getColumnIndexOrThrow("fotoMasc")),
                    idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow("idUsuario"))
                )
            )
        }

        cursor.close()
        db.close()
        return lista
    }
}