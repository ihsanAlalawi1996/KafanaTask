package com.example.ihsanstask.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ihsanstask.data.models.EventModel
import com.example.ihsanstask.data.repositories.EventsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(private val repository: EventsRepository) :
    ViewModel() {

    private val _eventsFlow: MutableStateFlow<List<EventModel>> =
        MutableStateFlow(listOf())
    val eventsFlow: StateFlow<List<EventModel>>
        get() = _eventsFlow.asStateFlow()

    init {
        getEvents()
    }

    fun getEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            _eventsFlow.emit(repository.getEvents())
        }
    }

    fun deleteEvents(events: Set<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            events.map {
                repository.removeEvent(it)
            }
            getEvents()
        }
    }
}
