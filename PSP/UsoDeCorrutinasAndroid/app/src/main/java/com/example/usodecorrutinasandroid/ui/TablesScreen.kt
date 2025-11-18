package com.example.usodecorrutinasandroid.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.usodecorrutinasandroid.viewmodel.TablesViewModel

@Composable
fun TablesScreen(viewModel: TablesViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    var from by remember { mutableStateOf(1) }
    var to by remember { mutableStateOf(10) }
    var upto by remember { mutableStateOf(10) }
    var artificialDelay by remember { mutableStateOf(0L) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        // Campos de entrada
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = from.toString(),
                onValueChange = { from = it.toIntOrNull() ?: from },
                label = { Text("Desde") },
                modifier = Modifier.width(100.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = to.toString(),
                onValueChange = { to = it.toIntOrNull() ?: to },
                label = { Text("Hasta") },
                modifier = Modifier.width(100.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = upto.toString(),
                onValueChange = { upto = it.toIntOrNull() ?: upto },
                label = { Text("Hasta N") },
                modifier = Modifier.width(120.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = artificialDelay.toString(),
                onValueChange = { artificialDelay = it.toLongOrNull() ?: artificialDelay },
                label = { Text("Delay ms (opt.)") },
                modifier = Modifier.width(160.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Button(
                onClick = { viewModel.generateTables(from, to, upto, artificialDelay) },
                enabled = !uiState.inProgress
            ) {
                Text("Generar")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { viewModel.cancelGeneration() }, enabled = uiState.inProgress) {
                Text("Cancelar")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        uiState.progressMessage?.let {
            Text("Estado: $it", style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (uiState.results.isEmpty() && !uiState.inProgress) {
            Text("No hay tablas generadas aún.", style = MaterialTheme.typography.bodyMedium)
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(uiState.results) { table ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp), elevation = CardDefaults.cardElevation()) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Tabla del ${table.n}", style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(6.dp))
                            table.rows.forEach { row ->
                                Text(row, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}
