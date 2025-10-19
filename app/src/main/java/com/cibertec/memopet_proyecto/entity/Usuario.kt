package com.cibertec.memopet_proyecto.entity

data class Usuario (
    var codigo : Int,
    var nombres : String = "",
    var apellidos : String = "",
    var correo : String = "",
    var clave : String = "")