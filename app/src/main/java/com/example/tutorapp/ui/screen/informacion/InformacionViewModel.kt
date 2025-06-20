package com.example.tutorapp.ui.screen.informacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutorapp.data.model.CarouselItem
import com.example.tutorapp.data.repository.CarouselRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InformacionViewModel: ViewModel()  {
    private val repository = CarouselRepository()

    private val _infoItems = MutableStateFlow<List<CarouselItem>>(emptyList())
    val infoItems: StateFlow<List<CarouselItem>> = _infoItems

    private val _seccionSeleccionado = MutableStateFlow<String?>(null)
    val seccionSeleccionado: StateFlow<String?> = _seccionSeleccionado

    private val _infoCargando = MutableStateFlow(false)
    val infoCargando: StateFlow<Boolean> = _infoCargando

    private val _infoTimeoutReached = MutableStateFlow(false)
    val infoTimeoutReached: StateFlow<Boolean> = _infoTimeoutReached

    private val _detalleSeleccionado = MutableStateFlow<CarouselItem?>(null)
    val detalleSeleccionado: StateFlow<CarouselItem?> = _detalleSeleccionado

    fun seleccionarSecccion(seccion: String) {
        _seccionSeleccionado.value = seccion
        getInformacion(seccion)
    }

    fun seleccionarDetalle(detalle: CarouselItem) {
        _detalleSeleccionado.value = detalle
    }

    fun getInformacion(seccion: String) {
        viewModelScope.launch {
            _infoCargando.value = true
            _infoTimeoutReached.value = true

            try {
                val lista = repository.getCarouselPorTipo(seccion)
                _infoItems.value = lista
                _infoTimeoutReached.value = lista.isEmpty()
            } catch (e: Exception) {
                _infoItems.value = emptyList()
                _infoTimeoutReached.value = true
            } finally {
                _infoCargando.value = false
            }
        }
    }

    fun limpiarSeleccion() {
        _seccionSeleccionado.value = null
        _infoItems.value = emptyList()
    }
}