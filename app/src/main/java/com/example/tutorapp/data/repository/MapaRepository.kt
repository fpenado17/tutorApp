package com.example.tutorapp.data.repository

import com.example.tutorapp.data.model.MapaItem
import com.example.tutorapp.data.network.RetrofitClient
import android.util.Log
import com.example.tutorapp.BuildConfig
import com.example.tutorapp.data.model.CatTipoUbicacion
import com.example.tutorapp.data.model.RutaResultado
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MapaRepository {
    private val api = RetrofitClient.apiService

    suspend fun getUbicaciones(tipos: List<String>): List<MapaItem> {
        return try {
            api.getUbicaciones(tipos)
        } catch (e: Exception) {
            Log.e("getUbicaciones", "Error al llamar API", e)
            emptyList()
        }
    }

    suspend fun getCatFiltros(): List<CatTipoUbicacion> {
        return try {
            api.getTipoUbicacion()
        } catch (e: Exception) {
            Log.e("getCatFiltros", "Error al llamar API", e)
            emptyList()
        }
    }

    suspend fun getRuta(origen: LatLng, destino: LatLng): RutaResultado? {
        return withContext(Dispatchers.IO) {
            try {
                val url =
                    "https://maps.googleapis.com/maps/api/directions/json?origin=${origen.latitude},${origen.longitude}&destination=${destino.latitude},${destino.longitude}&mode=walking&key=${BuildConfig.GOOGLE_MAPS_API_KEY}"

                val connection = URL(url).openConnection() as HttpURLConnection
                val data = connection.inputStream.bufferedReader().readText()
                val json = JSONObject(data)

                val ruta = json.getJSONArray("routes").getJSONObject(0)
                val leg = ruta.getJSONArray("legs").getJSONObject(0)
                val stepsJson = leg.getJSONArray("steps")

                val steps = mutableListOf<String>()

                for (i in 0 until stepsJson.length()) {
                    val stepJson = stepsJson.getJSONObject(i)
                    val polylinePoints = stepJson.getJSONObject("polyline").getString("points")
                    steps.add(polylinePoints)
                }

                val distancia = leg.getJSONObject("distance").getString("text")
                val duracion = leg.getJSONObject("duration").getString("text")

                RutaResultado(steps = steps, distancia = distancia, duracion = duracion)
            } catch (e: Exception) {
                Log.e("getRuta", "Error al obtener ruta", e)
                null
            }
        }
    }
}