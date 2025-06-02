package com.example.tutorapp.data.repository

import com.example.tutorapp.data.model.CarouselItem
import com.example.tutorapp.data.network.RetrofitClient
import android.util.Log

class CarouselRepository {
    private val api = RetrofitClient.apiService

    suspend fun getCarouselPorTipo(tipo: String, top: Int? = null): List<CarouselItem> {
        return try {
            val response = api.getInformacionPorTipo(tipo, top)
            response
        } catch (e: Exception) {
            Log.e("getCarouselPorTipo", "Error al llamar API", e)
            emptyList()
        }
    }
}