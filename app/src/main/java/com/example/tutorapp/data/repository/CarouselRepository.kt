package com.example.tutorapp.data.repository

import com.example.tutorapp.data.model.CarouselItem
import com.example.tutorapp.data.network.RetrofitClient
import android.util.Log
import com.example.tutorapp.data.model.Proceso

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

    suspend fun getProcesosBuscados(top: Int? = null): List<Proceso> {
        return try {
            val response = api.getProcesoBuscado(top)
            response
        } catch (e: Exception) {
            Log.e("getProcesosBuscados", "Error al llamar API", e)
            emptyList()
        }
    }
}