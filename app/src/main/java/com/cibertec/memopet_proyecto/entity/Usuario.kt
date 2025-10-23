package com.cibertec.memopet_proyecto.entity

data class Usuario (
    var idUsuario : Int=0,
    var nombres : String = "",
    var apellidos : String = "",
    var correo : String = "",
    var clave : String = "")