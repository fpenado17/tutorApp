package com.example.tutorapp.data.model

data class MapaItem(
    val _id: String,
    val nombre: String,
    val informacion: String,
    val codigo: String?,
    val latitud: Double,
    val longitud: Double,
    val tipo: String,
    val facultad: String,
    val imagenes: List<String> = emptyList(),
    val icono: String
)