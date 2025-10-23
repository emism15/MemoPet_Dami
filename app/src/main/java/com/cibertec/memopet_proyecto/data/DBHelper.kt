package com.cibertec.memopet_proyecto.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, "memopet.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {

        // Tabla USUARIO
        db.execSQL("""
            CREATE TABLE usuario (
                idUsuario INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                nombres TEXT,
                apellidos TEXT,
                correo TEXT UNIQUE,
                clave TEXT
            )
        """.trimIndent())

        // Tabla MASCOTA
        db.execSQL("""
            CREATE TABLE mascota (
                idMascota INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                nombre TEXT,
                especie TEXT,
                genero TEXT,
                color TEXT,
                esterilizado TEXT,
                fechaNacimiento TEXT,
                fotoMasc TEXT,
                idUsuario INTEGER,
                FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario)
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
        db.execSQL("DROP TABLE IF EXISTS usuario")
        db.execSQL("DROP TABLE IF EXISTS vacuna")
        db.execSQL("DROP TABLE IF EXISTS mascota")
        onCreate(db)
    }
}
