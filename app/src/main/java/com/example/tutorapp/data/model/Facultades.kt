package com.example.tutorapp.data.model

data class Facultades(
    val _id: String,
    val nombre: String,
    val codigo: String,
    val descripcion: String?,
    val carrera: List<Carrera>?
)

data class Carrera(
    val _id: String,
    val nombre: String,
    val codigo: String,
    val descripcion: String,
)
