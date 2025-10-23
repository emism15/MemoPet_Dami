package com.cibertec.memopet_proyecto.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.cibertec.memopet_proyecto.entity.Usuario


class UsuarioDAO (context: Context) {
    private val dbHelper = DBHelper(context)

    fun registrar(usuario: Usuario): Long {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("nombres", usuario.nombres)
            put("apellidos", usuario.apellidos)
            put("correo", usuario.correo)
            put("clave", usuario.clave)
        }
        val resultado = db.insert("usuario", null, valores)
        db.close()
        return resultado
    }

    fun verificar(correo: String, clave: String): Usuario? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM usuario WHERE correo = ? AND clave = ?",
            arrayOf(correo, clave)
        )
        var usuario: Usuario? = null
        if (cursor.moveToFirst()) {
            usuario = Usuario(
                idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow("idUsuario")),
                nombres = cursor.getString(cursor.getColumnIndexOrThrow("nombres")),
                apellidos = cursor.getString(cursor.getColumnIndexOrThrow("apellidos")),
                correo = cursor.getString(cursor.getColumnIndexOrThrow("correo")),
                clave = cursor.getString(cursor.getColumnIndexOrThrow("clave"))
            )
        }

        cursor.close()
        db.close()
        return usuario
    }

    // Verificar si el correo ya existe
    fun existeCorreo(correo: String): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT idUsuario FROM usuario WHERE correo = ?",
            arrayOf(correo))
        val existe = cursor.moveToFirst()
        cursor.close()
        db.close()
        return existe
    }
}