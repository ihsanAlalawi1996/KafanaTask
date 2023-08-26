package com.example.ihsanstask.ui.addEvent

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ihsanstask.data.models.EventModel
import com.example.ihsanstask.data.repositories.AddEventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(private val repository: AddEventRepository) :
    ViewModel() {

    var isTitle = ObservableField(false)
    var isLoading = ObservableField(false)

    var model = EventModel()

    private val _responseFlow: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val responseFlow: StateFlow<Boolean>
        get() = _responseFlow.asStateFlow()

    private fun EventModel.handleValidation(valid: () -> Unit) {
        if (getTitle().isEmpty())
            isTitle.set(true)
        else {
            isTitle.set(false)
            valid()
        }
    }

    fun setDates(date: String, hijriDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            model.date = date
            model.hijriDate = hijriDate
        }
    }

    fun save() {
        model.handleValidation {
            viewModelScope.launch(Dispatchers.IO) {
                isLoading.set(true)

                if (model.id == 0) repository.addEvent(model)
                else repository.updateEvent(model)
                _responseFlow.emit(true)
            }
        }
    }

    fun getEvent(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getEvent(id).apply {
                model.id = id
                model.date = date
                model.hijriDate = hijriDate
                model.setTitle(getTitle())
                model.setDescription(getDescription())
            }
        }
    }
}
