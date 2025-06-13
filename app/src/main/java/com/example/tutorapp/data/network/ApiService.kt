package com.example.tutorapp.data.network
import com.example.tutorapp.data.model.CarouselItem
import com.example.tutorapp.data.model.CatTipoUbicacion
import com.example.tutorapp.data.model.Facultades
import com.example.tutorapp.data.model.MapaItem
import com.example.tutorapp.data.model.NivelProceso
import com.example.tutorapp.data.model.Proceso
import com.example.tutorapp.data.model.ProcesoDetalle
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("public/informacion/general/")
    suspend fun getGeneralInformation(): List<CarouselItem>

    @GET("public/informacion/general/")
    suspend fun getInformacionPorTipo(
        @Query("tipo") tipo: String,
        @Query("top") top: Int? = null
    ): List<CarouselItem>

    @GET("public/ubicacion/")
    suspend fun getUbicaciones(
        @Query("tipo") tipos: List<String>
    ): List<MapaItem>

    @GET("public/tipo/ubicacion/")
    suspend fun getTipoUbicacion(): List<CatTipoUbicacion>

    @GET("public/nivel/procesos/")
    suspend fun getNivelProceso(
        @Query("sort") sort: String,
    ): List<NivelProceso>

    @GET("public/procesos/")
    suspend fun getProceso(
        @Query("codigo_nivel") codigo_nivel: String,
    ): List<Proceso>

    @GET("public/procesos/detalle")
    suspend fun getProcesoDetalle(
        @Query("codigo_proceso") codigo_proceso: String,
    ): List<ProcesoDetalle>

    @GET("public/procesos/detalle")
    suspend fun getProcesoDetalleFacultad(
        @Query("codigo_proceso") codigo_proceso: String,
        @Query("codigo_facultad") codigo_facultad: String,
    ): List<ProcesoDetalle>

    @GET("public/facultades")
    suspend fun getFacultades(): List<Facultades>
}