package com.example.tutorapp.ui.screen.procesos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutorapp.data.model.Facultades
import com.example.tutorapp.data.model.ProcesoDetalle
import com.example.tutorapp.data.repository.ProcesoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetalleProcesoViewModel(private val codigo: String, private val porFacultad: Boolean) : ViewModel() {
    private val repository = ProcesoRepository()

    private val _detalle = MutableStateFlow<List<ProcesoDetalle>>(emptyList())
    val detalle: StateFlow<List<ProcesoDetalle>> = _detalle

    private val _cargando = MutableStateFlow(true)
    val cargando: StateFlow<Boolean> = _cargando

    private val _facultades = MutableStateFlow<List<Facultades>>(emptyList())
    val facultades: StateFlow<List<Facultades>> = _facultades

    private val _facultadSeleccionada = MutableStateFlow<String?>(null)
    val facultadSeleccionada: StateFlow<String?> = _facultadSeleccionada

    init {
        if (porFacultad) {
            cargarFacultades()
        } else {
            cargarDetalle()
        }
    }

    private fun cargarDetalle(facultad: String? = null) {
        viewModelScope.launch {
            try {
                _cargando.value = true
                val data = if (facultad != null && porFacultad) {
                    repository.getProcesosDetallePorFacultad(codigo, facultad)
                } else {
                    repository.getProcesosDetalle(codigo)
                }
                _detalle.value = data
            } catch (e: Exception) {
                _detalle.value = emptyList()
            } finally {
                _cargando.value = false
            }
        }
    }

    private fun cargarFacultades() {
        viewModelScope.launch {
            try {
                _cargando.value = true
                val  data = repository.getFacultades()
                _facultades.value = data
            } catch (e: Exception) {
                _facultades.value = emptyList()
                _detalle.value = emptyList()
            } finally {
                _cargando.value = false
            }
        }
    }

    fun seleccionarFacultad(facultad: String) {
        _facultadSeleccionada.value = facultad
        cargarDetalle(facultad)
    }
}