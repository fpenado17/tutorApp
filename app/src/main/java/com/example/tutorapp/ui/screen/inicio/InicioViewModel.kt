package com.example.tutorapp.ui.screen.inicio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutorapp.data.model.CarouselItem
import com.example.tutorapp.data.model.Proceso
import com.example.tutorapp.data.repository.CarouselRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InicioViewModel: ViewModel() {
    private val repository = CarouselRepository()

    private val _generalItems = MutableStateFlow<List<CarouselItem>>(emptyList())
    val generalItems: StateFlow<List<CarouselItem>> = _generalItems

    private val _procesosItems = MutableStateFlow<List<Proceso>>(emptyList())
    val procesosItems: StateFlow<List<Proceso>> = _procesosItems

    init {
        getInformacionPorTipo("General")
        getInformacionProceso(10)
    }

    fun getInformacionPorTipo(tipo: String) {
        viewModelScope.launch {
            try {
                val items = when (tipo) {
                    "General" -> repository.getCarouselPorTipo(tipo)
                    else -> emptyList()
                }

                when (tipo) {
                    "General" -> _generalItems.value = items
                }
            } catch (e: Exception) {
                when (tipo) {
                    "General" -> _generalItems.value = emptyList()
                }
            }
        }
    }

    fun getInformacionProceso(top: Int? = null) {
        viewModelScope.launch {
            _procesosItems.value = repository.getProcesosBuscados(top)
        }
    }
}

