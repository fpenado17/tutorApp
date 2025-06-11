package com.example.tutorapp.data.repository

import android.util.Log
import com.example.tutorapp.data.model.NivelProceso
import com.example.tutorapp.data.model.Proceso
import com.example.tutorapp.data.network.RetrofitClient

class ProcesoRepository {
    private val api = RetrofitClient.apiService

    suspend fun getNiveles(sort: String): List<NivelProceso> {
        return try {
            api.getNivelProceso(sort)
        } catch (e: Exception) {
            Log.e("MapaRepository", "Error al llamar API", e)
            emptyList()
        }
    }

    suspend fun getProcesos(codigo_nivel: String): List<Proceso> {
        return try {
            api.getProceso(codigo_nivel)
        } catch (e: Exception) {
            Log.e("MapaRepository", "Error al llamar API", e)
            emptyList()
        }
    }
}