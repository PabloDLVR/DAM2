package com.example.usodecorrutinasandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usodecorrutinasenandroid.model.TableResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class TablesUiState(
    val results: List<TableResult> = emptyList(),
    val inProgress: Boolean = false,
    val progressMessage: String? = null
)

class TablesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TablesUiState())
    val uiState: StateFlow<TablesUiState> = _uiState

    private var generationJob: Job? = null

    fun generateTables(from: Int = 1, to: Int = 10, upto: Int = 10, delayPerRowMs: Long = 0L) {
        generationJob?.cancel()
        _uiState.update { it.copy(inProgress = true, results = emptyList(), progressMessage = "Iniciando...") }

        generationJob = viewModelScope.launch {
            try {
                val deferreds = (from..to).map { n ->
                    async(Dispatchers.Default) {
                        val rows = (1..upto).map { i ->
                            if (!isActive) throw CancellationException()
                            if (delayPerRowMs > 0) delay(delayPerRowMs)
                            "$n x $i = ${n * i}"
                        }
                        TableResult(n, rows)
                    }
                }
                val all = deferreds.awaitAll().sortedBy { it.n }
                _uiState.update { it.copy(results = all, inProgress = false, progressMessage = "Listo") }

            } catch (ce: CancellationException) {
                _uiState.update { it.copy(inProgress = false, progressMessage = "Cancelado") }
            } catch (t: Throwable) {
                _uiState.update { it.copy(inProgress = false, progressMessage = "Error: ${t.message}") }
            }
        }
    }

    fun cancelGeneration() {
        generationJob?.cancel()
        generationJob = null
        _uiState.update { it.copy(inProgress = false, progressMessage = "Cancelado por usuario") }
    }

    override fun onCleared() {
        super.onCleared()
        generationJob?.cancel()
    }
}
