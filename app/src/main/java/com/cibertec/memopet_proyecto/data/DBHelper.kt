package com.cibertec.memopet_proyecto.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, "memopet.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {

        // Tabla Mascota
        db.execSQL("""
            CREATE TABLE mascota(
                id_mascota INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                nombre TEXT,
                especie TEXT,
                fecha_nacimiento TEXT,
                foto_res_id INTEGER
            )
        """.trimIndent())

        // Tabla Vacuna
        db.execSQL("""
            CREATE TABLE vacuna(
                id_vacuna INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                mascota_id INTEGER,
                nombre_vacuna TEXT,
                fecha_aplicacion TEXT,
                FOREIGN KEY (mascota_id) REFERENCES mascota(id_mascota)
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS vacuna")
        db.execSQL("DROP TABLE IF EXISTS mascota")
        onCreate(db)
    }
}
