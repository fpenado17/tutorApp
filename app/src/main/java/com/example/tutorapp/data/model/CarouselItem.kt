package com.example.tutorapp.data.model

data class CarouselItem(
    val _id: String,
    val nombre: String,
    val descripcion: String? = null,
    val imagen: String? = null,
    val tipo: String? = null,
    val busqueda: Number? = null
)