package com.example.ihsanstask.ui.date

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ihsanstask.data.models.BaseModel
import com.example.ihsanstask.data.repositories.DateRepository
import com.example.ihsanstask.utils.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DateViewModel @Inject constructor(private val repository: DateRepository) : ViewModel() {


    private val _dateFlow: MutableStateFlow<Results<BaseModel?>> = MutableStateFlow(Results.start())
    val dateFlow: StateFlow<Results<BaseModel?>>
        get() = _dateFlow.asStateFlow()

    fun getHijriDate(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getHijriDate(date).collect {
                _dateFlow.emit(it)
            }
        }
    }

//    fun getHijriDate(date: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//
//            val ssss = async {
//                "Sdasdasda 1111".debugPrint()
//                "Sdasdasda 22222".debugPrint()
//                "Sdasdasda 33333".debugPrint()
//            }
//
//            val ssdsdsd = async {
//                "Sdasdasda 4444".debugPrint()
//                "Sdasdasda 5555".debugPrint()
//                "Sdasdasda 666".debugPrint()
//            }
//
//            val asdsda = async {
//                "Sdasdasda 77777".debugPrint()
//            }
//
//            val dvcbcvb = async {
//                "Sdasdasda 888888".debugPrint()
//            }
//
//            ssss.await()
//            ssdsdsd.await()
//            asdsda.await()
//            dvcbcvb.await()
//        }
//    }
}