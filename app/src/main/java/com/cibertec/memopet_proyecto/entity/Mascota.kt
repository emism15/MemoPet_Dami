package com.cibertec.memopet_proyecto.entity

data class Mascota(
    val idMascota: Int = 0,
    val nombre: String,
    val especie: String,
    val genero: String,
    val color: String,
    val esterilizado: String,
    val fechaNacimiento: String,
    val fotoMasc: String? = null,
    val idUsuario: Int
)