package com.example.tutorapp.ui.screen.mapas

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutorapp.data.model.CatTipoUbicacion
import com.example.tutorapp.data.model.MapaItem
import com.example.tutorapp.data.model.RutaResultado
import com.example.tutorapp.data.repository.MapaRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapaViewModel : ViewModel() {
    private val repository = MapaRepository()

    private val _marcadores = MutableStateFlow<List<MapaItem>>(emptyList())
    val marcadores: StateFlow<List<MapaItem>> = _marcadores

    private val _ruta = MutableStateFlow<RutaResultado?>(null)
    val ruta: StateFlow<RutaResultado?> = _ruta

    private val _mostrarRuta = mutableStateOf(false)
    val mostrarRuta: MutableState<Boolean> get() = _mostrarRuta

    private val _rutaSteps = mutableStateListOf<String>()
    val rutaSteps: List<String> get() = _rutaSteps

    private val _isLoading = mutableStateOf(false)
    val isLoading get() = _isLoading

    private val _textoBusqueda = MutableStateFlow("")
    val textoBusqueda: StateFlow<String> = _textoBusqueda

    private val _filtrosSeleccionados = MutableStateFlow<List<String>>(emptyList())
    val filtrosSeleccionados: StateFlow<List<String>> = _filtrosSeleccionados

    private val _catFiltros = MutableStateFlow<List<CatTipoUbicacion>>(emptyList())
    val catFiltros: StateFlow<List<CatTipoUbicacion>> = _catFiltros

    init {
        cargarCatFiltros()
        val filtrosIniciales = listOf("Facultad", "Salud", "Comedor")
        _filtrosSeleccionados.value = filtrosIniciales
        cargarMarcadores(filtrosIniciales)
    }

    fun cargarMarcadores(tipos: List<String> = listOf("Facultad", "Salud", "Comedor")) {
        viewModelScope.launch {
            try {
                val lista = repository.getUbicaciones(tipos)
                _marcadores.value = lista
            } catch (e: Exception) {
                _marcadores.value = emptyList()
            }
        }
    }

    fun cargarRuta(origen: LatLng, destino: LatLng) {
        viewModelScope.launch {
            _isLoading.value = true
            val resultado = repository.getRuta(origen, destino)
            _ruta.value = resultado

            // Limpia y carga los nuevos puntos de la ruta
            _rutaSteps.clear()
            resultado?.steps?.let { _rutaSteps.addAll(it) }

            // Muestra la ruta
            _mostrarRuta.value = true
            _isLoading.value = false
        }
    }

    fun cargarCatFiltros() {
        viewModelScope.launch {
            try {
                val lista = repository.getCatFiltros()
                _catFiltros.value = lista
            } catch (e: Exception) {
                _catFiltros.value = emptyList()
            }
        }
    }

    fun busquedaMapa(nuevoTexto: String) {
        _textoBusqueda.value = nuevoTexto
    }

    fun actualizarFiltros(nuevosFiltros: List<String>) {
        _filtrosSeleccionados.value = nuevosFiltros
        cargarMarcadores(nuevosFiltros)
    }
}