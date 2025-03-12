package com.example.ujian_advance.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujian_advance.data.EventRepository
import com.example.ujian_advance.data.UiState
import com.example.ujian_advance.data.entity.EventEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: EventRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<EventEntity>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<EventEntity>>> = _uiState.asStateFlow()

    init {
        fetchFavorites()
    }

    private fun fetchFavorites() {
        viewModelScope.launch {
            repository.getAllFavoriteEvents()
                .catch { e ->
                    _uiState.value = UiState.Error(e.message ?: "Unknown error occurred")
                }
                .collect { favorites ->
                    _uiState.value = UiState.Success(favorites)
                }
        }
    }
}