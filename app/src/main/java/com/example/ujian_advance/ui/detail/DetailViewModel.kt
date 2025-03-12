package com.example.ujian_advance.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujian_advance.data.EventRepository
import com.example.ujian_advance.data.UiState
import com.example.ujian_advance.data.entity.EventEntity
import com.example.ujian_advance.data.respone.Event
import com.example.ujian_advance.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailViewModel(private val repository: EventRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Event?>>(UiState.Loading)
    val uiState: StateFlow<UiState<Event?>> = _uiState.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    fun fetchEventDetails(eventId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val result = ApiConfig.getApiService().getDetailEvent(eventId)
                if (result.event != null) {
                    _uiState.value = UiState.Success(result.event)
                    checkFavoriteStatus(eventId)
                } else {
                    _uiState.value = UiState.Error("Event details not found")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    when (e) {
                        is HttpException -> "Error: ${e.code()}"
                        else -> "No internet connection"
                    }
                )
            }
        }
    }

    private fun checkFavoriteStatus(eventId: String) {
        viewModelScope.launch {
            _isFavorite.value = repository.getFavoriteEventById(eventId).firstOrNull() != null
        }
    }

    fun toggleFavorite() {
        val currentState = _uiState.value
        if (currentState !is UiState.Success || currentState.data == null) return
        val currentEvent = currentState.data
        val currentFavoriteStatus = _isFavorite.value

        viewModelScope.launch {
            if (currentFavoriteStatus) {
                repository.delete(EventEntity(
                    id = currentEvent.id.toString(),
                    name = currentEvent.name ?: "",
                    mediaCover = currentEvent.mediaCover ?: "",
                    category = currentEvent.category ?: "",
                    beginTime = currentEvent.beginTime ?: "",
                    imageLogo = currentEvent.imageLogo ?: "",
                    cityName = currentEvent.cityName ?: "",
                    quota = currentEvent.quota ?: 0,
                    summary = currentEvent.summary ?: "",
                    registrants = currentEvent.registrants ?: 0,
                    endTime = currentEvent.endTime ?: ""
                ))
            } else {
                repository.insert(
                    EventEntity(
                    id = currentEvent.id.toString(),
                    name = currentEvent.name ?: "",
                    mediaCover = currentEvent.mediaCover ?: "",
                    category = currentEvent.category ?: "",
                    beginTime = currentEvent.beginTime ?: "",
                    imageLogo = currentEvent.imageLogo ?: "",
                    cityName = currentEvent.cityName ?: "",
                    quota = currentEvent.quota ?: 0,
                    summary = currentEvent.summary ?: "",
                    registrants = currentEvent.registrants ?: 0,
                    endTime = currentEvent.endTime ?: ""
                )
                )
            }
            _isFavorite.value = !currentFavoriteStatus
        }
    }
}