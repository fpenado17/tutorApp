package com.example.tutorapp.data.model

data class Proceso(
    val _id: String,
    val nombre: String,
    val codigo_nivel: String,
    val codigo: String?,
    val por_facultad: Boolean,
    val descripcion: String?,
    val busqueda: Number?,
    val codigo_prerequisito: List<String>?
)
