package com.cibertec.memopet_proyecto.entity

data class Recordatorio(
    val id: Int,
    val mascotaId: Int,
    val tipo: String, // Vacunación, Desparasitación, Medicación
    val fecha: String
)