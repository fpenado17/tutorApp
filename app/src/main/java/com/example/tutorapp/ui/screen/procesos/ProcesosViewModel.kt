package com.example.tutorapp.ui.screen.procesos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutorapp.data.model.NivelProceso
import com.example.tutorapp.data.model.Proceso
import com.example.tutorapp.data.repository.ProcesoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProcesosViewModel : ViewModel() {
    private val repository = ProcesoRepository()

    private val _nivelProcesos = MutableStateFlow<List<NivelProceso>>(emptyList())
    val nivelProcesos: StateFlow<List<NivelProceso>> = _nivelProcesos

    private val _textoBusqueda = MutableStateFlow("")
    val textoBusqueda: StateFlow<String> = _textoBusqueda

    private val _codigoSeleccionado = MutableStateFlow<String?>(null)
    val codigoSeleccionado: StateFlow<String?> = _codigoSeleccionado

    private val _procesos = MutableStateFlow<List<Proceso>>(emptyList())
    val procesos: StateFlow<List<Proceso>> = _procesos

    private val _procesosCargando = MutableStateFlow(false)
    val procesosCargando: StateFlow<Boolean> = _procesosCargando

    private val _procesosTimeoutReached = MutableStateFlow(false)
    val procesosTimeoutReached: StateFlow<Boolean> = _procesosTimeoutReached

    private val _nivelesCargando = MutableStateFlow(false)
    val nivelesCargando: StateFlow<Boolean> = _nivelesCargando

    private val _nivelesTimeoutReached = MutableStateFlow(false)
    val nivelesTimeoutReached: StateFlow<Boolean> = _nivelesTimeoutReached

    init {
        cargarNiveles()
    }

    fun cargarNiveles(sort: String =  "orden") {
        viewModelScope.launch {
            _nivelesCargando.value = true
            _nivelesTimeoutReached.value = false

            try {
                val lista = repository.getNiveles(sort)
                _nivelProcesos.value = lista
                _nivelesTimeoutReached.value = lista.isEmpty()
            } catch (e: Exception) {
                _nivelProcesos.value = emptyList()
                _nivelesTimeoutReached.value = true
            } finally {
                _nivelesCargando.value = false
            }
        }
    }

    fun busquedaProceso(nuevoTexto: String) {
        _textoBusqueda.value = nuevoTexto
    }

    fun seleccionarCodigo(codigo: String) {
        _codigoSeleccionado.value = codigo
        cargarProcesos(codigo)
    }

    fun cargarProcesos(codigoNivel: String) {
        viewModelScope.launch {
            _procesosCargando.value = true
            _procesosTimeoutReached.value = false

            try {
                val lista = repository.getProcesos(codigoNivel)
                _procesos.value = lista
                _procesosTimeoutReached.value = lista.isEmpty()
            } catch (e: Exception) {
                _procesos.value = emptyList()
                _procesosTimeoutReached.value = true
            } finally {
                _procesosCargando.value = false
            }
        }
    }

    fun limpiarSeleccion() {
        _codigoSeleccionado.value = null
        _procesos.value = emptyList()
    }
}