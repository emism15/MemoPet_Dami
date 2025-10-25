package com.cibertec.memopet_proyecto.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.cibertec.memopet_proyecto.entity.Recordatorio

class RecordatorioDAO (context: Context) {

    private val dbHelper = DBHelper(context)

    fun agregar(recordatorio: Recordatorio): Long {
        val db = dbHelper.writableDatabase
        val cv = ContentValues().apply {
            put("idMascota", recordatorio.mascotaId)
            put("tipo", recordatorio.tipo)
            put("fecha", recordatorio.fecha)
            put("hora", recordatorio.hora)
            put("nota", recordatorio.nota)
        }
        val id = db.insert("recordatorio", null, cv)
        db.close()
        return id
    }

    fun obtenerPorMascota(idMascota: Int): List<Recordatorio> {
        val lista = mutableListOf<Recordatorio>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM recordatorio WHERE idMascota=?", arrayOf(idMascota.toString()))
        if (cursor.moveToFirst()) {
            do {
                lista.add(
                    Recordatorio(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        mascotaId = cursor.getInt(cursor.getColumnIndexOrThrow("mascotaId")),
                        tipo = cursor.getString(cursor.getColumnIndexOrThrow("tipo")),
                        fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha")),
                        hora = cursor.getString(cursor.getColumnIndexOrThrow("hora")),
                        nota = cursor.getString(cursor.getColumnIndexOrThrow("nota"))
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lista
    }
}