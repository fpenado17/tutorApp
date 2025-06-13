package com.example.tutorapp.ui.screen.procesos

import android.app.Activity
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tutorapp.R
import com.example.tutorapp.common.startVoiceRecognition
import com.example.tutorapp.ui.theme.PrincipalAqua
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.tutorapp.ui.screen.procesos.components.SimpleList

@Composable
fun ProcesoScreen(navController: NavController) {
    val viewModel: ProcesosViewModel = viewModel()
    val query by viewModel.textoBusqueda.collectAsState()
    val niveles by viewModel.nivelProcesos.collectAsState()
    val codigoSeleccionado by viewModel.codigoSeleccionado.collectAsState()
    val procesos by viewModel.procesos.collectAsState()
    val context = LocalContext.current
    val procesosCargando by viewModel.procesosCargando.collectAsState()
    val procesosTimeoutReached by viewModel.procesosTimeoutReached.collectAsState()
    val nivelesCargando by viewModel.nivelesCargando.collectAsState()
    val nivelesTimeoutReached by viewModel.nivelesTimeoutReached.collectAsState()
    val speechResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val spokenText = result.data
                ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                ?.firstOrNull()

            spokenText?.let {
                viewModel.busquedaProceso(it)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.cargarNiveles()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { },
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp),
                    placeholder = { Text("Buscar...", style = MaterialTheme.typography.bodySmall) },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodySmall,
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = PrincipalAqua,
                        unfocusedIndicatorColor = PrincipalAqua,
                        cursorColor = PrincipalAqua,
                        focusedPlaceholderColor = Color.Gray,
                        unfocusedPlaceholderColor = Color.Gray,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Buscar",
                            tint = PrincipalAqua
                        )
                    },
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            IconButton(onClick = { viewModel.busquedaProceso("") }) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "Limpiar",
                                )
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        viewModel.busquedaProceso(query)
                    })
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = { startVoiceRecognition(context, speechResultLauncher) }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outline_mic),
                        contentDescription = "Voz",
                        tint = PrincipalAqua
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (codigoSeleccionado == null) {
                SimpleList(
                    items = niveles,
                    isLoading = nivelesCargando,
                    timeoutReached = nivelesTimeoutReached,
                    emptyMessage = "No se encontraron procesos.",
                    onItemClick = { nivel -> viewModel.seleccionarCodigo(nivel.codigo) }
                ) { nivel ->
                    Column(
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                    ) {
                        Text(
                            nivel.nombre,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        Text(
                            nivel.descripcion ?: "",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }
            } else {
                SimpleList(
                    items = procesos,
                    isLoading = procesosCargando,
                    timeoutReached = procesosTimeoutReached,
                    emptyMessage = "No se encontraron procesos.",
                    onItemClick = { proceso ->
                        val codigo = proceso.codigo
                        val porFacultad = proceso.por_facultad.toString()
                        navController.navigate("detalle_proceso/$codigo/$porFacultad")
                    }
                ) { proceso ->
                    Column {
                        Text(proceso.nombre, style = MaterialTheme.typography.titleMedium, color = Color.White)
                        Text(
                            proceso.descripcion ?: "",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
        if (codigoSeleccionado != null) {
            FloatingActionButton(
                onClick = { viewModel.limpiarSeleccion() },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 12.dp, bottom = 10.dp)
                    .size(56.dp)
                    .border(
                        width = 1.dp,
                        color = PrincipalAqua,
                        shape = CircleShape
                    )
                    .clip(CircleShape),
                containerColor = Color.White,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Color.Black
                )
            }
        }
    }
}