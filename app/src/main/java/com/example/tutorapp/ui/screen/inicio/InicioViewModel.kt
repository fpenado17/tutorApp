package com.example.tutorapp.ui.screen.inicio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutorapp.data.model.CarouselItem
import com.example.tutorapp.data.repository.CarouselRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InicioViewModel: ViewModel() {
    private val repository = CarouselRepository()

    private val _generalItems = MutableStateFlow<List<CarouselItem>>(emptyList())
    val generalItems: StateFlow<List<CarouselItem>> = _generalItems

    private val _procesosItems = MutableStateFlow<List<CarouselItem>>(emptyList())
    val procesosItems: StateFlow<List<CarouselItem>> = _procesosItems

    init {
        getInformacionPorTipo("General")
        getInformacionPorTipo("Procesos", 10)
    }

    fun getInformacionPorTipo(tipo: String, top: Int? = null) {
        viewModelScope.launch {
            try {
                val items = when (tipo) {
                    "General" -> repository.getCarouselPorTipo(tipo)
                    "Procesos" -> repository.getCarouselPorTipo(tipo, top)
                    else -> emptyList()
                }

                when (tipo) {
                    "General" -> _generalItems.value = items
                    "Procesos" -> _procesosItems.value = items
                }
            } catch (e: Exception) {
                when (tipo) {
                    "General" -> _generalItems.value = emptyList()
                    "Procesos" -> _procesosItems.value = emptyList()
                }
            }
        }
    }
}

