package com.example.tutorapp.data.model

data class ProcesoDetalle(
    val _id: String,
    val titulo: String,
    val codigo_proceso: String,
    val codigo_facultad: String,
    val codigo_escuela: String,
    val codigo: String?,
    val descripcion: String?,
    val pasos: List<Paso>?
)

data class Paso(
    val _id: String,
    val nombre: String,
    val descripcion: String,
    val orden: Int,
    val documento: List<Documento>,
    val codigo_ubicacion: String,
    val costo: String,
    val url: String,
    val imagen: List<String>,
)

data class Documento(
    val _id: String,
    val nombre: String,
    val descripcion: String?,
    val orden: Int,
    val url: String
)