package com.example.tutorapp.data.repository

import android.util.Log
import com.example.tutorapp.data.model.Facultades
import com.example.tutorapp.data.model.NivelProceso
import com.example.tutorapp.data.model.Proceso
import com.example.tutorapp.data.model.ProcesoDetalle
import com.example.tutorapp.data.network.RetrofitClient

class ProcesoRepository {
    private val api = RetrofitClient.apiService

    suspend fun getNiveles(sort: String): List<NivelProceso> {
        return try {
            api.getNivelProceso(sort)
        } catch (e: Exception) {
            Log.e("getNiveles", "Error al llamar API", e)
            emptyList()
        }
    }

    suspend fun getProcesos(codigo_nivel: String): List<Proceso> {
        return try {
            api.getProceso(codigo_nivel)
        } catch (e: Exception) {
            Log.e("getProcesos", "Error al llamar API", e)
            emptyList()
        }
    }

    suspend fun getProcesosDetalle(codigo_proceso: String): List<ProcesoDetalle> {
        return try {
            api.getProcesoDetalle(codigo_proceso)
        } catch (e: Exception) {
            Log.e("getProcesosDetalle", "Error al llamar API", e)
            emptyList()
        }
    }

    suspend fun getFacultades(): List<Facultades> {
        return try {
            api.getFacultades()
        } catch (e: Exception) {
            Log.e("getFacultades", "Error al llamar API", e)
            emptyList()
        }
    }

    suspend fun getProcesosDetallePorFacultad(codigo_proceso: String,codigo_facultad: String): List<ProcesoDetalle> {
        return try {
            api.getProcesoDetalleFacultad(codigo_proceso, codigo_facultad)
        } catch (e: Exception) {
            Log.e("getProcesosDetallePorFacultad", "Error al llamar API", e)
            emptyList()
        }
    }
}