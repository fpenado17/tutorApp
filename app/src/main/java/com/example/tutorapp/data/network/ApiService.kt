package com.example.tutorapp.data.network
import com.example.tutorapp.data.model.CarouselItem
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
}