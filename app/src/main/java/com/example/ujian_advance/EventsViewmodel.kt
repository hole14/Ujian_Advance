package com.example.ujian_advance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujian_advance.data.EventRepository
import com.example.ujian_advance.data.UiState
import com.example.ujian_advance.data.entity.EventEntity
import com.example.ujian_advance.data.respone.ListEventsItem
import com.example.ujian_advance.data.retrofit.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import retrofit2.HttpException

class EventsViewmodel(private val apiService: ApiService, private val repository: EventRepository): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ListEventsItem>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<ListEventsItem>>> = _uiState.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    fun fetchEvents(active: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = apiService.getEvents(active)
                if (response.listEvents.isEmpty()) {
                    _uiState.value = UiState.Error("No events found")
                } else {
                    _uiState.value = UiState.Success(response.listEvents)
                    checkFavoriteStatus(response.listEvents[0].id.toString())
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
        val currentFavoriteStatus = _isFavorite.value

        viewModelScope.launch {
            if (currentFavoriteStatus) {
                repository.delete(
                    EventEntity(
                    id = currentState.data[0].id.toString(),
                    name = currentState.data[0].name,
                    mediaCover = currentState.data[0].mediaCover,
                    category = currentState.data[0].category,
                    beginTime = currentState.data[0].beginTime,
                    imageLogo = currentState.data[0].imageLogo,
                    cityName = currentState.data[0].cityName,
                    quota = currentState.data[0].quota,
                    summary = currentState.data[0].summary,
                    registrants = currentState.data[0].registrants,
                    endTime = currentState.data[0].endTime
                )
                )
            } else {
                repository.insert(
                    EventEntity(
                        id = currentState.data[0].id.toString(),
                        name = currentState.data[0].name,
                        mediaCover = currentState.data[0].mediaCover,
                        category = currentState.data[0].category,
                        beginTime = currentState.data[0].beginTime,
                        imageLogo = currentState.data[0].imageLogo,
                        cityName = currentState.data[0].cityName,
                        quota = currentState.data[0].quota,
                        summary = currentState.data[0].summary,
                        registrants = currentState.data[0].registrants,
                        endTime = currentState.data[0].endTime
                    )
                )
            }
            _isFavorite.value = !currentFavoriteStatus
        }
    }
}