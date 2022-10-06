package com.eposro.apps.nerdquotes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class MainViewModel : ViewModel() {
    private val _uiState: MutableLiveData<MainActivityState> = MutableLiveData(MainActivityState())
    val uiState get() = _uiState as LiveData<MainActivityState>

    var quote: String? = null

    fun loadQuote() {
        _uiState.postValue(_uiState.value!!.copy(isLoading = true))
        viewModelScope.launch {

            try {
                delay(5000) // Fake an API call
                _uiState.postValue(_uiState.value!!.copy(isLoading = false))
                quote = "The quick brown fox jumps over the lazy dog."
            } catch (e: IOException){
                // Most likely no internet connection available.
                _uiState.postValue(_uiState.value!!.copy(isLoading = false))
            } catch (e: HttpException){
                // Unexpected 2xx http response
                Timber.e(e)
            }
        }
    }
}