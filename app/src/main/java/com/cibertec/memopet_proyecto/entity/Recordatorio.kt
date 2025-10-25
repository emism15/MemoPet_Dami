package com.cibertec.memopet_proyecto.entity

data class Recordatorio(
    val id: Int = 0,
    val mascotaId: Int,
    val tipo: String, // Vacunación, Desparasitación, Medicación
    val fecha: String = "",
    val hora: String = "",
    val nota: String = ""
)